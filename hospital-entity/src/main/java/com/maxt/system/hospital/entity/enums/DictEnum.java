package com.maxt.system.hospital.entity.enums;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午5:08
 * @Version 1.0
 * @Description
 */
public enum DictEnum {
    HOSPITAL("HosType", "医院等级"),
    CERTIFICATES_TYPE("CertificatesType", "证件类型")
    ;

    private String dictCode;
    private String msg;

    DictEnum(String dictCode, String msg) {
        this.dictCode = dictCode;
        this.msg = msg;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
