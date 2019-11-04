package com.vluver.cbj.colegio.model

/**
 * Created by Vlover on 21/04/2018.
 */

class SearchUser {
    var userName: String? = null
    var userEmail: String? = null
    var userAvatar: String? = null
    var userUID: String? = null
    var userPrivacy: Int = 0
    var statefollow: Int = 0//0=esperando respuesta 1=seguido 2=no seguido
}
