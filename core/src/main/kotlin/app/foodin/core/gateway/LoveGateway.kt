package app.foodin.core.gateway

import app.foodin.domain.foodLove.Love
import app.foodin.domain.foodLove.LoveFilter

interface LoveGateway : BaseGateway<Love, LoveFilter>
