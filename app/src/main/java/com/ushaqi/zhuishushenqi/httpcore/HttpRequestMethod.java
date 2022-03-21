package com.ushaqi.zhuishushenqi.httpcore;

/**
 * http请求方式
 * Created by fly on 2016/10/31 0031.
 */
public enum HttpRequestMethod {
    GET,//请求类型
    POST,
    JSON,
    JSON_POST_ERROR,//发送错误日志特殊需求
    DOWN_LOAD,
    HEADJSON,
    UPLOADIMG,//上传图片
    ADJSON,
    PUT,
    DELETE;
}
