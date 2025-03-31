package com.ttasjwi.board.system.common.logging

import com.ttasjwi.board.system.common.logging.impl.DelegateLogger

fun getLogger(clazz: Class<*>): Logger = DelegateLogger(clazz)
