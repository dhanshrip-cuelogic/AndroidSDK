package com.loner.android.sdk.webservice.interfaces

import android.location.Location

interface LocationUpdateInerface {
    fun onLocationChange(location: Location?)
}