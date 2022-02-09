package com.huike.common.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.huike.common.core.domain.entity.SysDept;
import com.huike.common.core.domain.entity.SysMenu;
import com.huike.common.core.domain.entity.SysUser;


/**
 * Treeselect树结构实体类
 * 
 * 
 */
public class TreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    protected Object id;

    /** 节点名称 */
    protected String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    protected List<TreeSelect> children=new ArrayList<>();

    public TreeSelect()
    {

    }

    public TreeSelect(SysDept dept)
    {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

//    //组织和人员混合树，前端需要
//    public TreeSelect(SysDept dept, boolean hashUser)
//    {
//        this.id = dept.getDeptId();
//        this.label = dept.getDeptName();
//        this.disabled=hashUser;
//        this.children = dept.getChildren().stream().map(d->new TreeSelect(d,hashUser)).collect(Collectors.toList());
//        if(dept.getUsers()!=null&&!dept.getUsers().isEmpty()){
//            dept.getUsers().forEach(user->{
//                TreeSelect treeSelect=new TreeSelect();
//                treeSelect.setId(user.getUserId());
//                treeSelect.setLabel(user.getUserName());
//                treeSelect.setType(1);
//                this.children.add(treeSelect);
//            });
//        }
//    }

    public TreeSelect(SysMenu menu)
    {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

//    public TreeSelect(SysUser user)
//    {
//        this.id = user.getUserId();
//        this.label = user.getUserName();
//        this.type=1;
//    }

//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }

//    public List<TreeSelect> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<TreeSelect> users) {
//        this.users = users;
//    }


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public List<TreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeSelect> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeSelect{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
