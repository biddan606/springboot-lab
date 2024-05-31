package com.example.springbootlab.config.p6spy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import java.sql.SQLException;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.util.StringUtils;

public class P6SpyFormatter extends JdbcEventListener implements MessageFormattingStrategy {

    @Override
    public void onAfterGetConnection(ConnectionInformation connectionInformation, SQLException e) {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(getClass().getName());
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
            String sql, String url) {
        if (!StringUtils.hasText(sql)) {
            return "";
        }

        return "\n\n"
                + "##############################################################\n"
                + "###  Connection ID  : " + connectionId + "\n"
                + "###  Execution Time : " + elapsed + " ms\n"
                + "###  Category       : " + category + "\n"
                + "###  Prepared SQL   : " + prepared + "\n"
                + "###  Executed SQL   : " + highlight(formatSql(sql)) + "\n"
                + "##############################################################\n";
    }

    private String formatSql(String sql) {
        String lowerCaseSql = sql.trim().toLowerCase();

        return switch (getSqlType(lowerCaseSql)) {
            case DDL -> FormatStyle.DDL.getFormatter().format(sql);
            case BASIC -> FormatStyle.BASIC.getFormatter().format(sql);
            default -> sql;
        };
    }

    private SqlType getSqlType(String sql) {
        if (sql.startsWith("create") || sql.startsWith("alter") || sql.startsWith("comment")) {
            return SqlType.DDL;
        } else if (sql.startsWith("select") || sql.startsWith("insert") || sql.startsWith("update") || sql.startsWith(
                "delete")) {
            return SqlType.BASIC;
        }
        return SqlType.OTHER;
    }

    private String highlight(String sql) {
        return FormatStyle.HIGHLIGHT.getFormatter().format(sql);
    }

    private enum SqlType {
        DDL, BASIC, OTHER
    }
}
