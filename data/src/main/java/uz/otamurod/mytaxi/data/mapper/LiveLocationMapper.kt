package uz.otamurod.mytaxi.data.mapper

import uz.otamurod.mytaxi.data.database.entity.LiveLocationEntity as LiveLocationDto
import uz.otamurod.mytaxi.domain.model.LiveLocation as LiveLocationBo

object LiveLocationMapper {
    class LiveLocation internal constructor(private val dto: LiveLocationDto) {
        operator fun invoke(): LiveLocationBo = with(dto) {
            LiveLocationBo(
                latitude,
                longitude,
                timestamp
            )
        }
    }

    class LiveLocationEntity internal constructor(private val bo: LiveLocationBo) {
        operator fun invoke(): LiveLocationDto = with(bo) {
            LiveLocationDto(
                latitude = latitude,
                longitude = longitude,
                timestamp = timestamp
            )
        }
    }
}