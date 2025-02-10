package com.hireconnect.adapters.mapper;

import com.hireconnect.adapters.dto.user.JobApplyResponseDTO;
import com.hireconnect.adapters.dto.user.ListApplicationsResponseDTO;
import com.hireconnect.core.entity.JobVacanciesApplication;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapperJobApplications {

    private final ModelMapper modelMapper;

    public MapperJobApplications(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        this.modelMapper.typeMap(JobVacanciesApplication.class, ListApplicationsResponseDTO.ApplicationDTO.class)
                .addMappings(mapping -> {
                    mapping.map(src -> src.getJobVacancy().getDepartment().getCompany(),
                            ListApplicationsResponseDTO.ApplicationDTO::setCompany);
                    mapping.map(JobVacanciesApplication::getJobVacancy,
                            ListApplicationsResponseDTO.ApplicationDTO::setJob);
                });
    }

    public ListApplicationsResponseDTO toResponseDTO(List<JobVacanciesApplication> applications) {
        List<ListApplicationsResponseDTO.ApplicationDTO> applicationDTOList = applications.stream()
                .map(application -> modelMapper.map(application, ListApplicationsResponseDTO.ApplicationDTO.class))
                .toList();

        return new ListApplicationsResponseDTO(applicationDTOList);
    }

    public JobApplyResponseDTO toJobApplyResponseDTO(JobVacanciesApplication application) {
        return modelMapper.map(application, JobApplyResponseDTO.class);
    }
}
