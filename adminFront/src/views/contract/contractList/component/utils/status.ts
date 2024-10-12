import { ref } from 'vue'
export default function statusChangeButtion(status: string) {
  const buttonStatus = ref({
    view: false, //查看审批
    viewNew: false, //查看新合同
    quit: false, //退租
    viewQuit: false, //查看退租
    statover: false, //续租
    void: false, //作废
    change: false, //变更
    copy: false, //复制
    more: false //更多
  })
  switch (status) {
    case '16': //新建待审批
      buttonStatus.value.view = true
      buttonStatus.value.copy = true
      break
    case '1': //正常执行
      buttonStatus.value.quit = true
      buttonStatus.value.statover = true
      buttonStatus.value.void = true
      buttonStatus.value.change = true
      buttonStatus.value.copy = true
      break
    case '13': //待执行
      buttonStatus.value.quit = true
      buttonStatus.value.statover = true
      buttonStatus.value.void = true
      buttonStatus.value.change = true
      buttonStatus.value.copy = true
      break
    case '2': //变更待审核
      buttonStatus.value.view = true
      buttonStatus.value.viewNew = true
      buttonStatus.value.copy = true
      break
    case '4': //退租待审核
      buttonStatus.value.view = true
      buttonStatus.value.copy = true
      break
    case '5': //退租待执行
      buttonStatus.value.view = true
      buttonStatus.value.viewQuit = true
      buttonStatus.value.copy = true
      buttonStatus.value.void = true
      break
    case '6': //已退租
      buttonStatus.value.viewQuit = true
      buttonStatus.value.void = true
      buttonStatus.value.copy = true
      break
    case '14': //续租待审核
      buttonStatus.value.view = true
      buttonStatus.value.copy = true
      break
    case '7': //作废待审批
      buttonStatus.value.view = true
      buttonStatus.value.copy = true
      break
    case '11': //已作废
      buttonStatus.value.copy = true
      break
    case '15': //已到期
      buttonStatus.value.copy = true
      buttonStatus.value.statover = true
      buttonStatus.value.void = true
      break
  }
  buttonStatus.value.more = buttonStatus.value.copy || buttonStatus.value.change ? true : false
  buttonStatus.value.status = status
  return buttonStatus.value
}
