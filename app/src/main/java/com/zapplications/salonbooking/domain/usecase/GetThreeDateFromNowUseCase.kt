package com.zapplications.salonbooking.domain.usecase

import android.annotation.SuppressLint
import com.zapplications.salonbooking.domain.model.datetime.DateUiModel
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@SuppressLint("NewApi")
class GetThreeDateFromNowUseCase @Inject constructor() {

    /**
     * Generates a list of `DateUiModel` representing the current day and the next two days.
     *
     * This function calculates the current date using the system's default time zone, then
     * calculates the dates for the next two days. It then formats each date into a
     * `DateUiModel` which includes:
     *  - `formattedDate`: The date in "YYYY-MM-DD" format (e.g., "2023-10-27").
     *  - `dayOfWeekText`: The short display name of the day of the week (e.g., "Fri").
     *  - `dayOfMonthNumber`: The day of the month as a number (e.g., 27).
     *  - `month`: The short display name of the month (e.g., "Oct").
     *
     * The date formatting and day/month name retrieval are localized based on the
     * system's default locale.
     *
     * @return A list of three `DateUiModel` objects representing today, tomorrow, and the day after tomorrow.
     */
    operator fun invoke() : List<DateUiModel> {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val todayPlus1 = today.plus(1, DateTimeUnit.DAY)
        val todayPlus2 = today.plus(2, DateTimeUnit.DAY)
        today.month.getDisplayName(TextStyle.FULL, Locale.getDefault())

        val firstThreeDates = listOf(today, todayPlus1, todayPlus2).map { localDate ->
            DateUiModel(
                formattedDate = LocalDate.Format {
                    year(); char('-'); monthNumber(); char('-'); dayOfMonth()
                }.format(localDate),
                dayOfWeekText = localDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                dayOfMonthNumber = localDate.dayOfMonth,
                month = localDate.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            )
        }

        return firstThreeDates
    }
}
