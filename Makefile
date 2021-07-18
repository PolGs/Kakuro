all:
	mkdir -p Production
	mkdir -p EXE/DriverKakuro
	mkdir -p EXE/DriverCtrlDomini
	mkdir -p EXE/DriverCtrlGenerarKakuro
	mkdir -p EXE/DriverCasellaBlanca
	mkdir -p EXE/DriverCasellaNegra
	javac -d Production/ FONTS/Main.java FONTS/Domini/*.java FONTS/Domini/Drivers/*.java
	rm -f Production/Makefile
	echo "all:" > Production/Makefile
	echo "	mkdir -p temp" >> Production/Makefile
	echo "	echo "Manifest-Version: 1.0" > temp/MANIFEST.MF" >> Production/Makefile
	echo "	echo -n "" >> temp/MANIFEST.MF" >> Production/Makefile
	echo "	echo "Main-Class: Domini.Drivers.KakuroDriver" >> temp/MANIFEST.MF" >> Production/Makefile
	echo "	jar cfm ../EXE/DriverKakuro/DriverKakuro.jar temp/MANIFEST.MF Domini/Drivers/KakuroDriver.class Domini/*.class" >> Production/Makefile
	echo "	echo "Manifest-Version: 1.0" > temp/MANIFEST.MF" >> Production/Makefile
	echo "	echo -n "" >> temp/MANIFEST.MF" >> Production/Makefile
	echo "	echo "Main-Class: Domini.Drivers.DriverCtrlGenerarKakuro" >> temp/MANIFEST.MF" >> Production/Makefile
	echo "	jar cfm ../EXE/DriverCtrlGenerarKakuro/DriverCtrlGenerarKakuro.jar temp/MANIFEST.MF Domini/Drivers/DriverCtrlGenerarKakuro.class Domini/*.class" >> Production/Makefile
	echo "	echo "Manifest-Version: 1.0" > temp/MANIFEST.MF" >> Production/Makefile
	echo "	echo -n "" >> temp/MANIFEST.MF" >> Production/Makefile
	echo "	echo "Main-Class: Domini.Drivers.DriverCtrlDomini" >> temp/MANIFEST.MF" >> Production/Makefile
	echo "	jar cfm ../EXE/DriverCtrlDomini/DriverCtrlDomini.jar temp/MANIFEST.MF Domini/Drivers/DriverCtrlDomini.class Domini/*.class" >> Production/Makefile
	echo "	echo "Manifest-Version: 1.0" > temp/MANIFEST.MF" >> Production/Makefile
	echo "	echo -n "" >> temp/MANIFEST.MF" >> Production/Makefile
	echo "	echo "Main-Class: Domini.Drivers.CasellaBlancaDriver" >> temp/MANIFEST.MF" >> Production/Makefile
	echo "	jar cfm ../EXE/DriverCasellaBlanca/DriverCasellaBlanca.jar temp/MANIFEST.MF Domini/Drivers/CasellaBlancaDriver.class Domini/*.class" >> Production/Makefile
	echo "	echo "Manifest-Version: 1.0" > temp/MANIFEST.MF" >> Production/Makefile
	echo "	echo -n "" >> temp/MANIFEST.MF" >> Production/Makefile
	echo "	echo "Main-Class: Domini.Drivers.CasellaNegraDriver" >> temp/MANIFEST.MF" >> Production/Makefile
	echo "	jar cfm ../EXE/DriverCasellaNegra/DriverCasellaNegra.jar temp/MANIFEST.MF Domini/Drivers/CasellaNegraDriver.class Domini/*.class" >> Production/Makefile
	(cd Production; make all)
	rm -rf Production/temp
	rm -rf Production/Domini
	rm -rf Production/Main.class
	rm -rf Production/Makefile
	rm -rf Production
