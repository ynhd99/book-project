package com.example.room.common.response;


/**
 * @author yangan
 * @param: 返回码
 * @date 2018/12/26 18:55
 */
public enum ResponseEumn {

  SUCCESS("200","SUCCESS"),
  ERROR("500","系统错误"),
  EMPTY_DATA("","暂无数据"),
  NOT_FOUND("404","接口不存在"),
  FAIL("400","请求无效"),
  PARAM_ERROR("1","参数错误"),
  TENANT_ID_NOT_EXIST("301000", "商户id不能为空"),
  ID_NOT_EXIST("301001","id不能为空"),
  ADD_FIALD("301002","添加数据失败"),
  UPDATE_FIALD("301003","更新数据失败"),
  DELETE_FIALD("301004","删除数据失败"),
  AUDIT_FIALD("301005","操作失败"),
  DETAIL_NOT_EXIST("301006","详情不能为空"),
  ORGID_NOT_EXIST("301007","机构id不能为空"),
  CUSTORM_NOT_EXIST("301008","客户id不能为空"),
  TIME_NOT_EXIST("301009","时间不能为空"),
  OUT_NOT_EXIST("301010","出库失败"),
  EVENT_NOT_EXIST("301011","执行动作不正确"),
  AUDIT_STATUS_NOT_EXIST("301012","状态不存在或不可执行"),
  ORDER_OUT_FIALD("301013","订单出库失败")
  ;
  /**
   * 状态码
   */
  private final String code;
  /**
   * 描述信息
   */
  private final String desc;

  ResponseEumn(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }
}
