###########################################################
#
# Copyright (C) 2006-2014 OpenWrt.org
#
# This is free software, licensed under the GNU General Public License v2.
# See /LICENSE for more information.
#
###########################################################
include $(TOPDIR)/rules.mk

PKG_NAME:=remote_access

PKG_VERSION:=0

PKG_RELEASE:=1

PKG_SOURCE:=$(PKG_NAME)-$(PKG_VERSION).$(PKG_RELEASE).tar.gz

PKG_SOURCE_URL:=file://$(PWD)/../packages/release/


include $(INCLUDE_DIR)/package.mk

define Package/remote_access
 SECTION:=utils
 CATEGORY:=Venus board support
 TITLE:=Remote access services of Venus 
 DEPENDS:=+libmosquitto +libubox +libubus +libjson-c +libuci
endef

define Package/remote_access/description
Remote access services of Venus provides all services of application
endef

define Package/remote_access/Build/Compile
	$(MAKE) -C$(PKG_BUILD_DIR)
endef

define Package/remote_access/install
	$(INSTALL_DIR) $(1)/bin
	$(INSTALL_DIR) $(1)/etc/config
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/$(PKG_NAME) $(1)/bin/
	$(INSTALL_DATA) ./files/remote.conf $(1)/etc/config/remote
endef

$(eval $(call BuildPackage,remote_access))

