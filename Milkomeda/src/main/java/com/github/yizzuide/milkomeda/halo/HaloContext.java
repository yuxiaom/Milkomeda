package com.github.yizzuide.milkomeda.halo;

import com.github.yizzuide.milkomeda.universe.context.AopContextHolder;
import com.github.yizzuide.milkomeda.universe.metadata.HandlerMetaData;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HaloContext
 * 上下文信息
 *
 * @author yizzuide
 * @since 2.5.0
 * Create at 2020/01/30 22:40
 */
public class HaloContext implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * 监听类型的方法属性名
     */
    public static final String ATTR_TYPE = "type";

    private static Map<String, List<HandlerMetaData>> tableNameMap = new HashMap<>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        tableNameMap = AopContextHolder.getHandlerMetaData(HaloHandler.class, HaloListener.class, (annotation, metaData) -> {
            HaloListener haloListener = (HaloListener) annotation;
            // 设置其它属性方法的值
            Map<String, Object> attrs = new HashMap<>(2);
            attrs.put(ATTR_TYPE, haloListener.type());
            metaData.setAttributes(attrs);
            return haloListener.value();
        }, false, false);
    }

    static Map<String, List<HandlerMetaData>> getTableNameMap() {
        return tableNameMap;
    }
}
