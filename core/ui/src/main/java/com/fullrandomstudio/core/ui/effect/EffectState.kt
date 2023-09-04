package com.fullrandomstudio.core.ui.effect

import java.util.UUID

interface Effect

sealed class EffectState {
    object Idle : EffectState()

    data class DisplayEffect(
        val value: Effect,
        val id: String = UUID.randomUUID().toString()
    ) : EffectState()
}
