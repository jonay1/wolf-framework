package com.wolf.app.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Table(name = "T_SEQUENCE")
@Getter
@Setter
public class Sequence implements Serializable {
	@Id
	@Column(length = 32)
	private String catagory;
	@Column(length = 32)
	private String name;
	@Column(length = 64)
	private Long val;
}
