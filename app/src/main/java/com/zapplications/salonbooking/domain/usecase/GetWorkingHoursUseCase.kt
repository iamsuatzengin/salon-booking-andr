package com.zapplications.salonbooking.domain.usecase

import android.annotation.SuppressLint
import com.zapplications.salonbooking.domain.model.datetime.TimeUiModel
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalTime
import javax.inject.Inject

@SuppressLint("NewApi")
class GetWorkingHoursUseCase @Inject constructor() {

    /**
     * Generates a list of LocalTime objects representing working hours, starting from 9:00 AM
     * and incrementing by 30 minutes for a total of 20 intervals.
     *
     * @return A list of LocalTime objects representing the working hours.
     */
    operator fun invoke(): List<TimeUiModel> {
        val startTime = LocalTime(hour = 9, minute = 0)
        val intervalMinutes: Long = 30
        val totalIntervals = 20

        return generateSequence(startTime) {
            it.toJavaLocalTime().plusMinutes(intervalMinutes).toKotlinLocalTime()
        }.take(totalIntervals + 1).toList().map { time ->
            TimeUiModel(time = time, isAM = time.hour < 12)
        }
    }
}
