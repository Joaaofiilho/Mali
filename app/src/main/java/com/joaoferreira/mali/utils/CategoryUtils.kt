package com.joaoferreira.mali.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.joaoferreira.domain.utils.Categories
import com.joaoferreira.mali.R

@Composable
fun CategoryToString(category: Categories): String {
    return when(category) {
        Categories.FOOD -> stringResource(R.string.category_food)
        Categories.BEAUTY -> stringResource(R.string.category_beauty)
        Categories.DRINKS -> stringResource(R.string.category_drinks)
        Categories.CLEANING -> stringResource(R.string.category_cleaning)
        Categories.UTENSILS -> stringResource(R.string.category_utensils)
        Categories.TOYS -> stringResource(R.string.category_toys)
        Categories.TECHNOLOGY -> stringResource(R.string.category_technology)
        Categories.OTHERS -> stringResource(R.string.category_others)
    }
}