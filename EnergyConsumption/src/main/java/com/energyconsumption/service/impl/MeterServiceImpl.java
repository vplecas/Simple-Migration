package com.energyconsumption.service.impl;

import com.energyconsumption.domain.dto.MeterDto;
import com.energyconsumption.domain.entity.Meter;
import com.energyconsumption.domain.entity.Profile;
import com.energyconsumption.exception.DataNotFoundException;
import com.energyconsumption.repository.MeterRepository;
import com.energyconsumption.service.MeterService;
import com.energyconsumption.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeterServiceImpl implements MeterService {

    private final MeterRepository meterRepository;

    private final ProfileService profileService;

    @Autowired
    public MeterServiceImpl(MeterRepository meterRepository,
                            ProfileService profileService) {
        this.meterRepository = meterRepository;
        this.profileService = profileService;
    }

    @Override
    public Meter getById(Long profileId, Long meterId) {
        Optional<Meter> meter = meterRepository.findByProfileIdAndMeterId(profileId, meterId);

        if (meter.isEmpty()) {
            throw new DataNotFoundException(
                    String.format("Meter with profile id %s and meter id %s not found", profileId, meterId));
        }

        return meter.get();
    }

    @Override
    public List<Meter> getAll(Long profileId) {
        return meterRepository.findAllByProfileId(profileId);
    }

    @Override
    public Meter create(Long profileId, MeterDto meterDto) {
        Profile profile = profileService.getById(profileId);

        return meterRepository.save(Meter.Builder.newInstance()
                .meterIdentifier(meterDto.getMeterIdentifier())
                .profile(profile)
                .build());
    }

    @Override
    public Meter update(Long profileId, Long meterId, MeterDto meterDto) {
        Meter meter = getById(profileId, meterId);

        meter.setMeterIdentifier(meterDto.getMeterIdentifier());

        return meterRepository.save(meter);
    }

    @Override
    public void delete(Long profileId, Long meterId) {
        Meter meter = getById(profileId, meterId);

        meterRepository.delete(meter);
    }

}
