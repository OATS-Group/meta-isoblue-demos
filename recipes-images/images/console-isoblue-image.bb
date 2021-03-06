SUMMARY = "ISOBlue Embedded Linux Console Demo"

LICENSE = "MIT"

PV = "${TDX_VER_INT}"

#start of the resulting deployable tarball name
IMAGE_NAME = "Isoblue2_${MACHINE}_image"

SYSTEMD_DEFAULT_TARGET = "graphical.target"

#create the deployment directory-tree
require recipes-images/images/tdx-image-fstype.inc

IMAGE_LINGUAS = "en-us"
#IMAGE_LINGUAS = "de-de fr-fr en-gb en-us pt-br es-es kn-in ml-in ta-in"
#ROOTFS_POSTPROCESS_COMMAND += 'install_linguas; '

DISTRO_UPDATE_ALTERNATIVES ??= ""
ROOTFS_PKGMANAGE_PKGS ?= '${@base_conditional("ONLINE_PACKAGE_MANAGEMENT", "none", "", "${ROOTFS_PKGMANAGE} ${DISTRO_UPDATE_ALTERNATIVES}", d)}'

CONMANPKGS ?= "connman connman-systemd connman-plugin-loopback connman-plugin-ethernet connman-plugin-wifi connman-client"
CONMANPKGS_libc-uclibc = ""

#don't install some id databases
#BAD_RECOMMENDATIONS_append_colibri-vf += " udev-hwdb cpufrequtils "

#deploy the X server for the tegras
#this adds a few MB to the image, but all graphical HW acceleration is
#available only on top of X, this is not required for nouveau based build.
IMAGE_INSTALL_append_tegra = " ${XSERVER} xterm xclock"
IMAGE_INSTALL_append_tegra124 = " ${XSERVER} xterm xclock"

IMAGE_INSTALL += " \
    angstrom-packagegroup-boot \
    packagegroup-basic \
    udev-extra-rules \
    ${ROOTFS_PKGMANAGE_PKGS} \
    timestamp-service \
    packagegroup-base-extended \
    \
    isoblue-software \
    isoblue-misc \
    kafka-bin \
    gpsd \
    vim \
    chrony \
    pps-tools \
    modemmanager \
    networkmanager \
    \
    libedit \
    libmbim \
    libqmi \
    librdkafka \
    avro-c \
    python-kafka \
    python-avro \
    python-gps3 \
    \
    oracle-jse-jdk-arm32-vfp-hflt \
"

require recipes-images/images/tdx-extra.inc

IMAGE_DEV_MANAGER   = "udev"
IMAGE_INIT_MANAGER  = "systemd"
IMAGE_INITSCRIPTS   = " "
IMAGE_LOGIN_MANAGER = "busybox shadow"

inherit image
