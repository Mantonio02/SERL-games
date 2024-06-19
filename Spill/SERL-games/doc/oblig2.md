# Rapport – innlevering 2
Team: SERL games (gruppe1) - Tyra Fosheim Eide, Sebastian Refsnes, Jonathan Ski og Mathias Antonio Løland.

#Prosjektrapport

* Rollene fungerer bra - gruppemedelmmene ser litt løst på rollene, den som gjør jobben har rollen. Alle hjelper hverandre der det trengs. Men oppgavene er fordelt litt for å matche rollene - Jonathan har sett mest på testing, Tyra har sett mest på assets, Jonathan booker grupperom, Mathias ser på leveldesign.

* Fint med to møter i uken. Vi har ikke fått gjort noe code review ennå, dette bør inkorporeres.

* Gruppedynamikken fungerer veldig bra med god kommunikasjon.

* Vi har ikke gjort sprint review/sprint planning så veldig bra frem til nå, men det har komt på plass på nyligste gruppemøte.

* Tyra hadde litt få commits første innlevering fordi det tok veldig lang tid å sette seg inn i Box2D. Mathias har hatt få commits denne uken fordi han holder på med en planleggingsintensiv oppgave. Sebastian har fått litt få oppgaver i denne ukens sprint, så en bedre fordeling i neste sprint bør prøves på.

* Link til møtereferatene siden forrige innlevering (bla ned til 23.02-08.03): [referat](../GRUPPEMØTER.md)

* Til neste sprint skal vi prøve oss på code review og vi ønsker å jobbe litt mer sammen om de forskjellige issusene.

# Krav og spesifikasjon

Siden forrige gang har vi jobbet med å nå målene for MVPen. Vi har ikke nådd alle målene ennå, men vi har prioritert de mest basise oppgavene først (som tegning av player, map). Så prioriterte vi interaksjon mellom Player og bakgrunn, samt opprettet våpen for player og deretter enemies.
Interaksjon mellom Player og Enemies vil bli prioritert i neste sprint.
Ny funksjonalitet prioriteres på bakgrunn av hvor nødvendig den er for 1) spillets funksjon og 2) spillerens opplevelse med spillet. F.eks. er enemies viktigere enn npcs, player health er viktigere enn visuell health bar osv.

**Krav vi har jobbet med**:
##### Vise et spillebrett
*"Som spiller trenger jeg å kunne se spillbrettet slik at jeg kan ser hva som skjer i spillet."*

Implementere Model som extender Game med en GameScreen som extender ScreenAdapter.

Designet et map i Tiled og implementer OrthographicTiledMapRenderer som viser mapet i GameScreen.

##### Vise spiller på spillebrett

*"Som spiller trenger jeg å kunne se spilleren på brettet slik at jeg vet hvor karakteren min er og kan bevege seg."* 

Oprettet en Player som extender Actor og representert Player i Model på en Stage som vises i GameScreen.

##### Flytte spiller med taster

*"Som spiller trenger jeg å kunnne flytte spilleren med taster slik at jeg kan få tilgang til de andre elementene i spillet."* 

InputHandler for Player som tar imot tastetrykk og metoder i Player for hvordan den skal bevege seg på skjermen - laget AnimationHandler for animasjonene.

##### Spiller interagerer med terreng

*"Som spiller trenger jeg at spiller kan interagere med terrenget slik at jeg kan komme videre i storyen."*

Implementert Box2D og gitt interagerbare objekter en Body (spiller, enemies, terreng).

Skrevet manuell sjekk for kollisjon i ModelTest.

##### Vise fiender som interagerer med terreng og spiller

*"Som spiller trenger jeg å se fiender på spillbrettet slik at jeg kan avgjøre hvordan jeg skal styre spillfiguren."*

Implementere Enemies og representert disse i Model.

Gitt enemies en Body som kan inteagere med Player i modellens World.

##### Spiller kan bruke et våpen.

*"Som spiller trenger jeg å se våpenet til spilleren slik at jeg kan justere spillingen min og nå objektivet med spillet."*

Implementert Weapon klasse.

##### Start-skjerm ved oppstart

*"Som spiller trenger jeg en start-skjerm ved oppstart slik at jeg kan få en følelse av spillet før jeg starter."* 

Implementert StartMenuScreen og generelle instillinger for GameScreens i ScreenConfigurations.

---

**Bugs**
Vi har ingen kjente bugs i koden til denne innleveringen.

# Produkt og kode
* linket til gruppemøte-referat i denne innleveringen.

* Prosjektet kan bygges på Linux, Mac-OS, og Windows

* Klasse diagram:

![Class Diagram](./Doxygraph_Oblig2.png?raw=true "Klasse Diagram")
