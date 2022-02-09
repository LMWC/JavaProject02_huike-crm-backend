package com.huike.clues.domain.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huike.clues.domain.TbClue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClueInfoVO extends TbClue{

	//线索归属
	private String assignUserName;
	
	//归属时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date lastAssignTime;
}
