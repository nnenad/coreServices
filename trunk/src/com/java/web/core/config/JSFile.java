package com.java.web.core.config;

public class JSFile implements Comparable<JSFile> {

    private String name;
    private boolean isStandard;
    private boolean isCard;
    private boolean isLogin;
    private boolean isFirst;
    private int rank;

    public static String getOnlyName(String name) {
        String result = name;
        if (result.contains("/")) {
            int pos = result.lastIndexOf("/");
            result = result.substring(pos+1);
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(boolean isStandard) {
        this.isStandard = isStandard;
    }

    public boolean getIsCard() {
        return isCard;
    }

    public void setIsCard(boolean isCard) {
        this.isCard = isCard;
    }

    public boolean getIsLogin() {
        return isLogin;
    }
    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean getIsFirst() {
        return isFirst;
    }
    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int compareTo(JSFile o) {
        if (o == null) return 1;
        if (this.rank < o.rank) return -1;
        if (this.rank > o.rank) return 1;
        return 0;
    }
}
