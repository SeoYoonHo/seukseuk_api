package com.lsyh.seukseuk.mapper;

import com.lsyh.seukseuk.domain.Holiday;
import com.lsyh.seukseuk.dto.HolidayDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface HolidayMapper {
    HolidayMapper INSTANCE = Mappers.getMapper(HolidayMapper.class);

    HolidayDTO.HolidayResponse holidayToHolidayResponseDto(Holiday holiday);
}
