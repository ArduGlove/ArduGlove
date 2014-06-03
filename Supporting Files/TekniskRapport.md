# ArduGlove - teknisk rapport

## Mathias Stang, Vegard Søyseth og Erik Vesteraas

Prosjektets mål har vært å lage et nytt verktøy for en brukergruppe ved å utforske inputmetoder som brukeren opplever som naturlig og intuitiv.

Resultatet av prosjektet er hansken ArduGlove, og medfølgende programvare. ArduGlove kan tilpasses flere typer input til informasjonssystemer. For eksempel kan hansken brukes til å spille spill eller brukes som en erstatning for, eller som et supplement til, mus og tastatur.

## Videoen

[https://vimeo.com/97207325](https://vimeo.com/97207325)

Videoen demonstrerer hanskens funksjonalitet i bruk samt forklarer hvordan den fungerer.

### Transkripsjon:

>“Vi er ArduGlove-teamet og vi har bragt interaksjon fra science fiction inn i dagens virkelighet!”

>"ArduGlove er en hanske som lar deg bruke håndbevegelser til å styre spill. Den kan brukes til å holde presentasjoner uten å være bundet til en maskin eller begrenset av en liten fjernkontroll. Den kan også brukes som hjelpemiddel i kontorsammenheng eller helt alminnelig surfing."

>"Hva som skjer når dataene kommer inn i maskinen kommer an på programmet. Vanligvis vil det tolke dataene og generere input til andre programmer, men for eksempel spill kan ta imot data direkte og bruke dem på måter vi aldri hadde funnet på selv." 

>"ArduGlove-systemet består av en trådløs hanske og en mottakerboks."

>"For å få elektronikken på hansken så kompakt som mulig har vi brukt Arduino Pro Mini. Denne samler inn data fra sensorene og sender det videre til den trådløse transcieveren."

>"Den viktigste sensorkomponenten er et kombinert akselerometer og gyroskop som gir oss data om håndens bevegelser og orientering i seks akser"

>"Kontaktsensorer laget av enkel strømførende sytråd aktiveres når man tar fingrene sammen."

>"Flex-sensorer gir informasjon om hvor mye hver enkelt finger er bøyd."

>"Trådløs kommunikasjon skjer via en transciever basert på norsk teknologi"

>"For å drive komponentene på hansken har vi et Lithium Polymer-batteri som bruker den mest plasseffektive batteriteknologien på markedet. I tillegg inneholder hansken en kompakt lader til batteriet og en god spenningsomformer for å gi strøm til de andre komponentene."

>"Hansken sender data fra transcieveren til en mottakerboks koblet til datamaskinen. Vi har jobbet hardt for å få responstiden så lav som mulig, og med en responstid på bare 5 millisekunder vil man ikke merke noen forsinkelse”.

>"Mottakerboksen er svært enkel og består av en Arduino Uno og en transciever av samme type som i hansken."

>"Det er egentlig bare fantasien som setter grenser for hva ArduGlove kan brukes til. Takket være en åpen protokoll og åpen kildekode er det rett og slett et hav av uutforskede muligheter som venter på å bli utnyttet!"

## Teknisk dokumentasjon

All kode samt noen tekniske dokumenter(inkludert dette) ligger i et åpent git-repo på følgende adresse:

[https://bitbucket.org/arduglove/arduglove](https://bitbucket.org/arduglove/arduglove)

Den mest vesentlige koden er:

- `Arduino/WirelessSender` - koden til selve hansken
- `Arduino/WirelessReceiver` - koden til mottakerboksen
- `Java/MKInterface` - koden som kjører på maskinen og gjør om til mus og tastatur-input

### Komponenter i hansken

- [Arduino Pro Mini 3.3V](https://www.sparkfun.com/products/11114)
- [Pololu 3.3V Step-Up/Step-Down Voltage Regulator S7V8F3](http://www.pololu.com/product/2122)
- [850 mAh LiPo-batteri](https://www.sparkfun.com/products/341)
- [Liten lader til LiPo-batteri](https://www.sparkfun.com/products/10217)
- [nRF24L01+ Transceiver-modul](http://www.ebay.com/sch/i.html?_nkw=nrf24l01)
- [GY-9150, Breakout board for MPU-9150](http://www.ebay.com/sch/i.html?_nkw=gy-9150)
- [Flexpoint Flex-sensorer](http://www.flexpoint.com)
- [Strømførende tråd](https://www.sparkfun.com/products/10867)

Flexpoint selger vanligvis bare i store kvantum og har dessverre ingen distributører. Man kan alltids sende dem en e-post og høre slik vi gjorde, men et alternativ er [noen fra Spectra Symbol](https://www.sparkfun.com/products/10264), men de er litt mer skjøre og ikke like enkle å koble til. Man kan også lage en nyttig hanske helt uten flex-sensorer.

Dersom man dropper flex-sensorer og bytter ut GY-9150 med [GY-521](http://www.ebay.com/sch/i.html?_nkw=gy-521) kan man sette sammen en hanske for under 300kr som fortsatt er kompatibel med mottakerboksen og programvaren.

### Komponenter i mottakeren

- [Arduino Uno med støtte for systemspenning på 3.3V (Freaduino Uno)](http://www.elecfreaks.com/store/freaduino-uno-rev18-mbefuno-p-414.html)
- [nRF24L01+ Transceiver-modul](http://www.ebay.com/sch/i.html?_nkw=nrf24l01)

Mottakerboksen kan gjøres mye mindre dersom man bruker f.eks. Arduino Pro Micro i stedet for Arduino Uno.

[Spesifikasjon av protokollen inn til maskina](https://bitbucket.org/arduglove/arduglove/src/fb02235df6ee/Supporting%20Files/ProtocolSpec.md?at=master)

### Biblioteker som er brukt i koden

- Arduino
    - [RF24](https://github.com/TMRh20/RF24)
    - [Sparkfun MPU-9150-kode](https://github.com/sparkfun/MPU-9150_Breakout)
- Java
    - [jSSC](https://github.com/scream3r/java-simple-serial-connector)
    - [JCommander](http://jcommander.org)

### Mer utfyllende om MKInterface

MKInterface er altså programmet som kjører på maskina og gjør om data fra hansken til input ved hjelp av [java.awt.Robot](http://docs.oracle.com/javase/7/docs/api/java/awt/Robot.html). Programmet har flere forskjellige modus og er i hovedsak en kommandolinje-applikasjon. Litt om de forskjellige modusene:

#### MouseMode - mouse

Med MouseMode styrer man musepekeren ved å "bikke" på hånda. Hvor mye man bikker styrer hvor fort musepekeren beveger seg.

#### GyroPointerMode - gyro

GyroPointerMode bruker håndens rotasjoner til å styre musa i stedet for MouseMode som bruker håndas orientering. Dette gjør at styringen føles mer som å peke på skjermen.

#### ArrowKeyMode - arrow

ArrowKeyMode genererer input til spill som kan styres med piltaster, som for eksempel konsoll-emulatorer, slik vi har vist i videoen med Mario og Zelda.

#### Andre modus

I tillegg til disse har vi WasdKeyMode, som er en variant av ArrowKeyMode, men gir WASD-taster i stedet for piltaster og PointerMode, som kom mellom MouseMode og GyroPointerMode og er en slags mellomting.
