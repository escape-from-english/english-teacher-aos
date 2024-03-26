package com.teacher.english.ui.navigate

import com.teacher.english.R

enum class MainNavRoutes(
    val titleResId: Int,
    val activeIconResId: Int,
    val inactiveIconResId: Int
) {
    Quiz(
        titleResId = R.string.quiz_tab_title,
        activeIconResId = R.drawable.baseline_quiz_24,
        inactiveIconResId = R.drawable.baseline_quiz_24
    ),
    WordsList(
        titleResId = R.string.words_tab_title,
        activeIconResId = R.drawable.baseline_checklist_rtl_24,
        inactiveIconResId = R.drawable.baseline_checklist_rtl_24
    ),
    Profile(
        titleResId = R.string.profile_tab_title,
        activeIconResId = R.drawable.baseline_person_24,
        inactiveIconResId = R.drawable.baseline_perm_identity_24
    ),
}