package com.mystify.ums.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.esotericsoftware.kryo.serializers.FieldSerializer.Optional;
import com.mystify.common.base.BaseModel;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
@TableName("ums_user")
public class UmsUser extends BaseModel {

    private static final long serialVersionUID = 1L;
   
    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	
    /**
     * 帐号
     */
	private String username;
    /**
     * 密码MD5(密码+盐)
     */
	private String password;
    /**
     * 盐
     */
	private String salt;
    /**
     * 姓名
     */
	private String realname;
    /**
     * 头像
     */
	private String avatar;
    /**
     * 电话
     */
	private String phone;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 性别
     */
	private Integer sex;
    /**
     * 状态(0:正常,1:锁定)
     */
	private Integer locked;
    /**
     * 创建时间
     */
	private Long ctime;


	public String getUsername() {
		return username;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public Long getCtime() {
		return ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

}
