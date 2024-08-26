package com.arkmon.autocicd.mappers;

import com.arkmon.autocicd.domains.model.ReleaseNote;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReleaseNoteMapper extends BaseMapper<ReleaseNote> {

    List<ReleaseNote> selectByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
