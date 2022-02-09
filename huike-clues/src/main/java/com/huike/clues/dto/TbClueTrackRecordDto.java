package com.huike.clues.dto;

import com.huike.common.core.dto.PageRequestDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 			<if test="clueId != null  and clueId != ''"> and clue_id = #{clueId}</if>
            <if test="subject != null  and subject != ''"> and subject = #{subject}</if>
            <if test="record != null  and record != ''"> and record = #{record}</if>
            <if test="level != null  and level != ''"> and level = #{level}</if>
            <if test="type != null  and type != ''"> and type = #{type}</if>
            <if test="falseReason != null  and falseReason != ''"> and false_reason = #{falseReason}</if>
 * @author 86150
 *
 */
@Data
public class TbClueTrackRecordDto  extends PageRequestDto{
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="clueId",name="线索id")
	private String clueId;
	
	@ApiModelProperty(value="subject",name="意向等级")
	private String subject;

	@ApiModelProperty(value="record",name="跟进记录")
	private String record;
	
	@ApiModelProperty(value="level",name="意向等级")
	private String level;
	
	@ApiModelProperty(value="type",name="0 正常跟进记录 1 伪线索 ")
	private String type;
	
	@ApiModelProperty(value="falseReason",name="原因")
	private String falseReason;
}
