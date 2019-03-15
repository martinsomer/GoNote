# GoNote
Lihtne Androidi rakendus märkmete tegemieks.

## Seadistamine
* Lae projekt GitHubist alla ning paki lahti
* Ava Android Studio ning vali **File** > **Open...**
* Avanevast aknast leia projekt ning ava see

## Juhendid
Oma projektis kasutasin järgnevaid juhendeid funktsioonide lisamiseks:
* [Room andmebaasid](https://www.youtube.com/watch?v=Ta4pw2nUUE4) (osad 24 - 28)
* [Navigation Drawer](https://developer.android.com/training/implementing-navigation/nav-drawer)
* [Fragmendid](https://guides.codepath.com/android/creating-and-using-fragments)
* [Adapterid](https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)

## Arvamus juhenditest
Room'i juhendis oleks võinud autor veidi rohkem seletada erinevate funktsioonide, eriti *Annotation*'ite kohta. Mõnda asja saab teha mitut moodi (nt andmete kustutamine andmebaasist), nii et oleks võinud olla mingi võrdlus nende vahel, miks üks on parem kui teine, või miks ta just nii teeb. Kuid see õpetus andis asjast piisava ülevaate, et ise luua andmebaas hoopis erineva struktuuri ja tabelitega ilma probleemideta.

Kõik teised juhendid olid väga head ja lihtsasti arusaadavad ning andsid piisavalt informatsiooni antud teema kohta. Nende õpetuste suur pluss oli see, et nad ei ajanud asja keeruliseks, vaid näitasid lihtsa näite abil, kuidas asi toimib.

## SDK versiooni muutused
Room'i juhendis oli teegi versioon aegunud, ning seal demonstreeritud 1.0.0 asemel kasutasin vesiooni 1.1.1.
Teiste juhendite puhul SDK versioonidega probleeme ei esinenud.

## Muud lisatud elemendid
Rakenduses on kasutatud juhendeid vaid osade funktsioonide lisamiseks; rakendus ei põhine ühelgi juhendil (vaid kasutab nendest osasid funktsioone). Erinevalt juhenditest on Navigation Drawer dünaamiliselt genereeritav. Sellesse on lisatud "Home" valik, mis viib koduekraanile, ja "Add Category". Märkmeid ja kategooriaid saab lisada, muuta ja kustutada. Nende salvestamiseks on loodud andmebaasid kategooriate ja märkmete jaoks. Rakenduse teema ja värviskeem on täielikult muudetud. Lisatud on Toolbar vajalike nuppudega märkmete ja kategooriate haldamiseks. Märkmete lisamiseks tegin Floating Action Buttoni. Märkmete puudumise või tühja kategooria korral näidatakse kasutajale illustreerivat pilti.

## Demo
Demo on vaadatav [siin](https://drive.google.com/file/d/1_-zg5NUsEnG_suj0bZpfcSa1uben3IBg/view?usp=sharing).
