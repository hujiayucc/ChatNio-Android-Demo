package com.hujiayucc.chatnio.android.bean;

/**
 * 角色
 */
public enum Role {
    /**
     * system
     */
    SYSTEM("system"),
    /**
     * user
     */
    USER("user"),
    /**
     * assistant
     */
    ASSISTANT("assistant"),
    /**
     * 未知
     */
    NULL("");

    private final String role;

    /**
     * 角色
     * @param role role
     */
    Role(String role) {
        this.role = role;
    }

    /**
     * 角色名称
     * @return 角色名称
     */
    public String getRole() {
        return role;
    }

    /**
     * 获取角色名称
     * @param role role
     * @return 角色名称
     */
    public static Role getRole(String role) {
        switch (role) {
            case "system":
                return SYSTEM;
            case "user":
                return USER;
            case "assistant":
            default:
                return ASSISTANT;
        }
    }
}