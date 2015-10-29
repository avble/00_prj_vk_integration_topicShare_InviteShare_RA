###########################################################
#
# Copyright (C) 2006-2014 OpenWrt.org
#
# This is free software, licensed under the GNU General Public License v2.
# See /LICENSE for more information.
#
###########################################################
include $(TOPDIR)/rules.mk

PKG_NAME:=alljoyn-topic

PKG_VERSION:=0

PKG_RELEASE:=1

#TODO: should fine-tuning this include and dependent library 
TARGET_CFLAGS += -I$(STAGING_DIR)/usr/include
TARGET_LDFLAGS += -L$(STAGING_DIR)/usr/lib
TARGET_LDFLAGS += -Wall -O0 -DQCC_OS_GROUP_POSIX -lalljoyn_c -lalljoyn -lalljoyn_about -lcrypto -lssl -lubus -lubox -lblobmsg_json -lsqlite3

PKG_SOURCE:=$(PKG_NAME)-$(PKG_VERSION).$(PKG_RELEASE).tar.gz

PKG_SOURCE_URL:=file://$(PWD)/../packages/release/


include $(INCLUDE_DIR)/package.mk

define Package/alljoyn-topic
  SECTION:=utils
  CATEGORY:=Venus board support
  TITLE:=Alljoyn topic of Venus
  DEPENDS:=+libopenssl +alljoyn +alljoyn-c +alljoyn-about +libubus +libubox +libblobmsg-json +libsqlite3
endef

define Package/alljoyn-topic/description
Alljoyn topic of Venus provides all services of application
endef

define Package/alljoyn-topic/Build/Compile
	$(MAKE) -C$(PKG_BUILD_DIR)
endef

define Package/alljoyn-topic/install
	$(INSTALL_DIR) $(1)/bin
	$(INSTALL_DIR) $(1)/etc
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/$(PKG_NAME) $(1)/bin/
	$(INSTALL_DATA) ./files/* $(1)/etc/
endef

$(eval $(call BuildPackage,alljoyn-topic))
