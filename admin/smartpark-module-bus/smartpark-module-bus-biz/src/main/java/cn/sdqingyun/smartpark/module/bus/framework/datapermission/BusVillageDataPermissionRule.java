package cn.sdqingyun.smartpark.module.bus.framework.datapermission;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.datapermission.core.rule.DataPermissionRule;
import cn.sdqingyun.smartpark.framework.mybatis.core.util.MyBatisUtils;
import cn.sdqingyun.smartpark.framework.web.core.util.WebFrameworkUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import org.apache.tika.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName DemoDataPermissionRule
 * @Description 建筑楼宇数据权限
 * @Author SUNk
 * @Date 2024/7/23 9:54
 * @ModifyDate 2024/7/23 9:54
 * @Version 1.0
 */
@Component
public class BusVillageDataPermissionRule implements DataPermissionRule {
    @Override
    public Set<String> getTableNames() {
        
        return Sets.newHashSet(
                "village", 
                "village_build",
                "build_collection",
                "contract_template"
//                "owner",
//                "village_user"
        );
    }

    @Override
    public Expression getExpression(String tableName, Alias tableAlias) {
        String  substring = WebFrameworkUtils.getDeptIdList();
        if (StringUtils.isBlank(substring)){
            return null;
        }

        String deptIdList = substring.substring( 1, substring.length() - 1 );
        //处理参数
        JSONArray jsonArray = JSON.parseArray(deptIdList);

        List<Long> idList = new ArrayList<>();
        List<Long> buildList = new ArrayList<>();
        List<Long> checkedList = new ArrayList<>();
        String type = jsonArray.getJSONObject(0).getString("type");

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            long id = obj.getLongValue("id");
            idList.add(id);

            JSONArray buildArray = obj.getJSONArray("build");
            List<Long> build = new ArrayList<>();
            for (int j = 0; j < buildArray.size(); j++) {
                build.add(buildArray.getLong(j));
            }
            buildList.addAll(build);

            JSONArray checkedArray = obj.getJSONArray("checkedBuild");
            List<Long> checked = new ArrayList<>();
            for (int j = 0; j < checkedArray.size(); j++) {
                checked.add(checkedArray.getLong(j));
            }
            checkedList.addAll(checked);
        }


        switch (tableName){
            case "village":
                return new AndExpression(new InExpression(MyBatisUtils.buildColumn(tableName, tableAlias, "id"), getExpressionList(idList))
                        ,new EqualsTo(MyBatisUtils.buildColumn(tableName, tableAlias, "type"), new StringValue(type)));
            case "village_build":
                return new InExpression(MyBatisUtils.buildColumn(tableName, tableAlias, "id"), getExpressionList(buildList));
            case "build_collection":
                if(CollUtil.isEmpty( checkedList )){
                    return null;
                }
                return new InExpression(MyBatisUtils.buildColumn(tableName, tableAlias, "id"), getExpressionList(checkedList));
            case "contract_template":
                return buildContractExpression(MyBatisUtils.buildColumn(tableName, tableAlias, "relation_builds"), buildList);
//            case "owne":
//                return buildContractExpression(MyBatisUtils.buildColumn(tableName, tableAlias, "build_bind"), buildList);
//            case "village_user":
//                return new InExpression(MyBatisUtils.buildColumn(tableName, tableAlias, "village_id"), getExpressionList(idList));
            default:
                return null;
        }
    }

    private ExpressionList getExpressionList(List<Long> idList) {
        ExpressionList expressionList = new ExpressionList();
        expressionList.setExpressions(idList.stream()
                .map(id -> new LongValue(id))
                .collect(Collectors.toList()));
        return expressionList;
    }

    private Expression buildContractExpression(Column buildBindColumn, List<Long> checkedList) {
        List<Expression> orExpressions = new ArrayList<>();
        for (Long checkedId : checkedList) {
            // 使用 FIND_IN_SET 函数来检查 build_bind 字段中是否包含某个 ID
            Function findInSetFunction = new Function();
            findInSetFunction.setName("FIND_IN_SET");
            findInSetFunction.setParameters(new ExpressionList(new LongValue(checkedId), buildBindColumn));

            orExpressions.add(findInSetFunction);
        }

        // 如果列表为空，返回 null
        if (orExpressions.isEmpty()) {
            return null;
        }

        // 将所有条件用 OR 连接起来
        if (orExpressions.size() == 1) {
            return orExpressions.get(0); // 只有一个条件时，直接返回
        } else {
            OrExpression orExpression = new OrExpression(orExpressions.get(0), orExpressions.get(1));
            for (int i = 2; i < orExpressions.size(); i++) {
                orExpression = new OrExpression(orExpression, orExpressions.get(i));
            }
            return orExpression;
        }
    }
}
