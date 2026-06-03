package com.util;

import com.bean.User;
import com.constant.SessionConstants;

public class RoleUtil {

    public static final int ROLE_ID_ADMIN = 1;
    public static final int ROLE_ID_OWNER = 2;

    private RoleUtil() {}

    /**
     * 解析角色：优先 role 字符串，否则按 role_id 映射（1=admin, 2=owner）
     */
    public static String resolveRole(User user) {
        if (user == null) {
            return null;
        }
        String role = user.getRole();
        if (role != null && !role.trim().isEmpty()) {
            role = role.trim();
            if ("admin".equalsIgnoreCase(role) || "系统管理员".equals(role)) {
                return SessionConstants.ROLE_ADMIN;
            }
            if ("owner".equalsIgnoreCase(role) || "小区业主".equals(role) || "业主".equals(role)) {
                return SessionConstants.ROLE_OWNER;
            }
            return role;
        }
        Integer roleId = user.getRoleId();
        if (roleId != null) {
            if (roleId == ROLE_ID_ADMIN) {
                return SessionConstants.ROLE_ADMIN;
            }
            if (roleId == ROLE_ID_OWNER) {
                return SessionConstants.ROLE_OWNER;
            }
        }
        return null;
    }

    public static boolean isAdmin(User user) {
        return SessionConstants.ROLE_ADMIN.equals(resolveRole(user));
    }

    public static boolean isOwner(User user) {
        return SessionConstants.ROLE_OWNER.equals(resolveRole(user));
    }

    /** 补全 Session 中 User 的 role 字段，避免后续模块判权失败 */
    public static void ensureRole(User user) {
        if (user == null) {
            return;
        }
        String resolved = resolveRole(user);
        if (resolved != null) {
            user.setRole(resolved);
        }
    }
}
