package cn.sdqingyun.smartpark.module.system.service.oauth2;

import cn.hutool.core.util.RandomUtil;
import cn.sdqingyun.smartpark.framework.common.enums.UserTypeEnum;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.test.core.ut.BaseDbUnitTest;
import cn.sdqingyun.smartpark.module.system.dal.dataobject.oauth2.OAuth2CodeDO;
import cn.sdqingyun.smartpark.module.system.dal.mysql.oauth2.OAuth2CodeMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.test.core.util.AssertUtils.assertPojoEquals;
import static cn.sdqingyun.smartpark.framework.test.core.util.AssertUtils.assertServiceException;
import static cn.sdqingyun.smartpark.framework.test.core.util.RandomUtils.*;
import static cn.sdqingyun.smartpark.module.system.enums.ErrorCodeConstants.OAUTH2_CODE_EXPIRE;
import static cn.sdqingyun.smartpark.module.system.enums.ErrorCodeConstants.OAUTH2_CODE_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link OAuth2CodeServiceImpl} 的单元测试类
 *
 * @author 智慧园区
 */
@Import(OAuth2CodeServiceImpl.class)
class OAuth2CodeServiceImplTest extends BaseDbUnitTest {

    @Resource
    private OAuth2CodeServiceImpl oauth2CodeService;

    @Resource
    private OAuth2CodeMapper oauth2CodeMapper;

    @Test
    public void testCreateAuthorizationCode() {
        // 准备参数
        Long userId = randomLongId();
        Integer userType = RandomUtil.randomEle(UserTypeEnum.values()).getValue();
        String clientId = randomString();
        List<String> scopes = Lists.newArrayList("read", "write");
        String redirectUri = randomString();
        String state = randomString();

        // 调用
        OAuth2CodeDO codeDO = oauth2CodeService.createAuthorizationCode(userId, userType, clientId,
                scopes, redirectUri, state);
        // 断言
        OAuth2CodeDO dbCodeDO = oauth2CodeMapper.selectByCode(codeDO.getCode());
        assertPojoEquals(codeDO, dbCodeDO, "createTime", "updateTime", "deleted");
        assertEquals(userId, codeDO.getUserId());
        assertEquals(userType, codeDO.getUserType());
        assertEquals(clientId, codeDO.getClientId());
        assertEquals(scopes, codeDO.getScopes());
        assertEquals(redirectUri, codeDO.getRedirectUri());
        assertEquals(state, codeDO.getState());
        assertFalse(DateUtils.isExpired(codeDO.getExpiresTime()));
    }

    @Test
    public void testConsumeAuthorizationCode_null() {
        // 调用，并断言
        assertServiceException(() -> oauth2CodeService.consumeAuthorizationCode(randomString()),
                OAUTH2_CODE_NOT_EXISTS);
    }

    @Test
    public void testConsumeAuthorizationCode_expired() {
        // 准备参数
        String code = "test_code";
        // mock 数据
        OAuth2CodeDO codeDO = randomPojo(OAuth2CodeDO.class).setCode(code)
                .setExpiresTime(LocalDateTime.now().minusDays(1));
        oauth2CodeMapper.insert(codeDO);

        // 调用，并断言
        assertServiceException(() -> oauth2CodeService.consumeAuthorizationCode(code),
                OAUTH2_CODE_EXPIRE);
    }

    @Test
    public void testConsumeAuthorizationCode_success() {
        // 准备参数
        String code = "test_code";
        // mock 数据
        OAuth2CodeDO codeDO = randomPojo(OAuth2CodeDO.class).setCode(code)
                .setExpiresTime(LocalDateTime.now().plusDays(1));
        oauth2CodeMapper.insert(codeDO);

        // 调用
        OAuth2CodeDO result = oauth2CodeService.consumeAuthorizationCode(code);
        assertPojoEquals(codeDO, result);
        assertNull(oauth2CodeMapper.selectByCode(code));
    }

}
