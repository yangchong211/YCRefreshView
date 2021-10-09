package org.yczbj.ycrefreshview.cover;

import java.util.ArrayList;

/**
 * 组数据的实体类
 */
public class GroupEntity {

    private String header;
    private String footer;
    private ArrayList<ChildEntity> children;
    private boolean isExpand;

    public GroupEntity(String header, String footer, ArrayList<ChildEntity> children) {
        this.header = header;
        this.footer = footer;
        this.children = children;
    }

    public GroupEntity(String header, String footer, ArrayList<ChildEntity> children, boolean isExpand) {
        this.header = header;
        this.footer = footer;
        this.children = children;
        this.isExpand = isExpand;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public ArrayList<ChildEntity> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ChildEntity> children) {
        this.children = children;
    }


    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

}
