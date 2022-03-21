package com.ushaqi.zhuishushenqi.httpdns;

import android.content.Context;
import android.os.Build;

import java.net.URL;
import java.util.ArrayList;

/**
 * 接入阿里云防DNS劫持SDK
 * Created by fly on 2017/1/9 0009.
 */
public class AliHttpDnsConfig {
    /**
     * 阿里服务key
     * add fly
     */
    private final static String ACCOUNT_ID = "185442";
    private final static String SECRET_KEY = "48495907c116dbf992406902b7e15208";

//    private static HttpDnsService httpdns;

    static {
        try {
//            httpdns = HttpDns.getService(MyApplication.getInstance(), ACCOUNT_ID, SECRET_KEY);
            // DegradationFilter用于自定义降级逻辑
            // 通过实现shouldDegradeHttpDNS方法，可以根据需要，选择是否降级
//            DegradationFilter filter = new DegradationFilter() {
//                @Override
//                public boolean shouldDegradeHttpDNS(String hostName) {
//                    // 参照HttpDNS API文档，当存在中间HTTP代理时，应选择降级，使用Local DNS
//                    return detectIfProxyExist(MyApplication.getInstance());
//                }
//            };
//            // 将filter传进httpdns，解析时会回调shouldDegradeHttpDNS方法，判断是否降级
//            httpdns.setDegradationFilter(filter);
//            httpdns.setPreResolveAfterNetworkChanged(true);
            // 设置预解析域名列表
            //httpdns.setPreResolveHosts(setPreResolveHosts());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置预解析域名列表
     * <p>注意
     * <p>
     * 点：(遇到问题阿里云只要host,不需要带http前缀)</p>
     * add fly
     */
    private static ArrayList setPreResolveHosts() throws Exception {
        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add((new URL(HttpDnsManager.CHAPTER_URL_HOST).getHost()));
//        for (String urlStr : DistributeRootUrlManager.distributeRootUrlArr) {
//            //在这里使用URL 不适用正则匹配
//            URL url = new URL(urlStr);
//            arrayList.add(url.getHost());
//        }
        return arrayList;
    }


    /**
     * 设置http 请求
     * add fly
     *
     * @param url http 请求
     */
    public static URL getHttpdnsUrl(URL url) {
        String newUrl = url.toString();
        try {
            String host = url.getHost();
//            String ip = httpdns.getIpByHostAsync(host);  // 异步接口获取IP
//            if (!StringUtils.isEmpty(ip)) {
//                newUrl = newUrl.replaceFirst(host, ip);   // 通过HTTPDNS获取IP成功，进行URL替换和HOST头设置
//                return new URL(newUrl);
//            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 检测系统是否已经设置代理，请参考HttpDNS API文档。(阿里demo 方法)
     * add fly
     */
    private static boolean detectIfProxyExist(Context ctx) {
        boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyHost;
        int proxyPort;
        if (IS_ICS_OR_LATER) {
            proxyHost = System.getProperty("http.proxyHost");
            String port = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt(port != null ? port : "-1");
        } else {
            proxyHost = android.net.Proxy.getHost(ctx);
            proxyPort = android.net.Proxy.getPort(ctx);
        }
        return proxyHost != null && proxyPort != -1;
    }
}
