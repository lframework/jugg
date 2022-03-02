package com.lframework.starter.mybatis.utils;

import com.lframework.starter.mybatis.config.OpLogConfiguration;
import com.lframework.starter.mybatis.service.IOpLogsService;
import com.lframework.starter.mybatis.vo.CreateOpLogsVo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 操作日志Util
 *
 * @author zmj
 */
@Slf4j
public class OpLogUtil {

    private static final ThreadLocal<Map<String, Map<String, Object>>> VARIABLE_POOL = new ThreadLocal<>();

    private static final ThreadLocal<List<String>> LOG_ID_POOL = new ThreadLocal<>();

    private static final ThreadLocal<Map<String, Object>> EXTRA_POOL = new ThreadLocal<>();

    public static void init(String logId) {

        initPool();

        LOG_ID_POOL.get().add(logId);

        VARIABLE_POOL.get().putIfAbsent(logId, new HashMap<>());
    }

    public static void addLog(CreateOpLogsVo vo) {

        ExecutorService pool = (ExecutorService) ApplicationUtil.getBean(OpLogConfiguration.OP_LOG_THREAD_POOL_NAME);
        IOpLogsService opLogsService = ApplicationUtil.getBean(IOpLogsService.class);

        pool.execute(() -> {
            opLogsService.create(vo);
        });
    }

    public static void setVariable(String key, Object value) {

        VARIABLE_POOL.get().get(getCurrentLogId()).put(key, value);
    }

    public static Map<String, Object> getVariables() {

        return VARIABLE_POOL.get().get(getCurrentLogId());
    }

    public static String getExtra() {

        Object value = EXTRA_POOL.get().get(getCurrentLogId());

        return value == null ? null : JsonUtil.toJsonString(value);
    }

    public static void setExtra(Object value) {

        EXTRA_POOL.get().put(getCurrentLogId(), value);
    }

    public static void clear() {

        VARIABLE_POOL.get().remove(getCurrentLogId());
        EXTRA_POOL.get().remove(getCurrentLogId());
        LOG_ID_POOL.get().remove(LOG_ID_POOL.get().size() - 1);
    }

    private static void initPool() {

        if (VARIABLE_POOL.get() == null) {
            VARIABLE_POOL.set(new HashMap<>());
        }

        if (LOG_ID_POOL.get() == null) {
            LOG_ID_POOL.set(new ArrayList<>());
        }

        if (EXTRA_POOL.get() == null) {
            EXTRA_POOL.set(new HashMap<>());
        }
    }

    private static String getCurrentLogId() {

        return LOG_ID_POOL.get().get(LOG_ID_POOL.get().size() - 1);
    }
}
