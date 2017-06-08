package com.mystify.ums.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.mystify.common.base.BaseModel;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 组织
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
@TableName("ums_organization")
public class UmsOrganization extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 所属上级
     */
	private Integer pid;
    /**
     * 组织名称
     */
	private String name;
    /**
     * 组织描述
     */
	private String description;
    /**
     * 创建时间
     */
	private Long ctime;


	/*public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}*/
	

	public Integer getPid() {
		return pid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCtime() {
		return ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

}
