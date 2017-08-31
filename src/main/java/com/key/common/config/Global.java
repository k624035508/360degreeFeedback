package com.key.common.config;

import com.ckfinder.connector.ServletContextFactory;
import com.google.common.collect.Maps;
import com.key.common.utils.PropertiesLoader;
import com.key.common.utils.StringUtils;
import com.key.common.utils.web.Struts2Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by jielao on 2017/8/12.
 */
public class Global {
    /**
     * 当前对象实例
     */
    private static Global global = new Global();

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 上传文件基础虚拟路径
     */
    public static final String USERFILES_BASE_URL = "/userfiles/";

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader loader = new PropertiesLoader("config.properties");

    /**
     * 获取配置
     * @see ${fns:getConfig('adminPath')}
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null){
            value = loader.getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }

    /**
     * 获取上传文件的根目录
     * @return
     */
    public static String getUserfilesBaseDir() {
        HttpServletRequest request = Struts2Utils.getRequest();
        String dir = getConfig("userfiles.basedir");
        if (StringUtils.isBlank(dir)){
            try {
                dir = ServletContextFactory.getServletContext().getRealPath("/");
//                dir = request.getServletContext().getRealPath("/");
            } catch (Exception e) {
                return "";
            }
        }
        if(!dir.endsWith("/")) {
            dir += "/";
        }
		System.out.println("userfiles.basedir: " + dir);
        return dir;
    }
}
