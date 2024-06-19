# Rapport – innlevering 4
**Team:** *SERL Games* – *Tyra Fosheim Eide, Mathias Antonio Løland, Sebastian Refsnes, Jonathan Ski*

Før møtet gjorde vi en runde på å vise frem det vi hadde gjort den uken. Det var ingen sprint review siden prosjektet er "ferdig".

# Prosjektrapport

Rollene i teamet har fungert fint som vanlig, og vi har ikke sett noe behov for å endre disse.

Sist uken av prosjektet var veldig hektisk for mange i teamet og vi har ikke fått avsluttet prosjektet helt slik vi ønsket, men er likevel fornøyd med innsatsen vi har lagt ned og resultatet.

Litt lite bruk av kanban-boardet denne innleveringen. Det har gjort det litt vanskelig å vite hvordan prosjektet ligger an i innspurten.

I innspurten ble arbeidsfordelingen litt spontan i og med at ting måtte bli ferdig innen en gitt tid. Vi har derfor ikke så god oversikt over hvor mye hver enkelt team-medlem har gjort.

Referat finnes som vanlig (her)[./GRUPPEMØTER.md]

Generelt gjennom dette prosjektet har vi hatt god kommunikasjon med jevnlige møter og oversiktlige referater fra gruppemøtene. Hadde vi skullet begynt prosjektet på nytt nå ville vi hatt bedre oversikt over biblioteker vi brukte, og dermed fått litt bedre tid. Det gikk mye tid med til å sette seg inn i det. Energien ebbet litt ut utover i prosjektet. Det hadde også vært fint å ha et større fokus på testing og dokumentasjon underveis så vi slapp å gjøre alt i siste liten. Da kunne vi også fått nytte av det, og ikke bare følt at det ble gjort for å få poeng på innleveringen.

# Krav og spesifikasjoner

**Krav vi har jobbet med:**

*"Som spiller ønsker jeg å møte sterkere fiender som markerer progresjon i spillet slik at jeg får en følelse av mestring."*

Lage assets for boss

Implementere Boss og rutiner for spawning av hjelpere til bossen.

**Implementere Sword etter kravene for Weapons**

*"Som spiller ønsker jeg muligheten til å bruke flere våpen så jeg kan tilpasse karakteren min slik jeg ønsker og velge min fortrukne spillestil."*

Fikse assetsene til Sword slik at det fungerer med Weapon-klassen.

**Overgang mellom maps**

*"Som spiller ønsker jeg å kunne gå videre til neste område i spillet slik at jeg kan komme videre i storyen."*

Implementere representasjon av levels.

Implementere klasse for Door som kan ta spilleren til det neste levelet.

Implementere metoder for å bytte hvilket level modellen viser.

**Fjerne rester av fiender når man går videre til neste eller når man dreper fiender**

*"Som spiller trenger jeg at det kun er det nåværende levelet som vises, og at ingen fiender fra det forrige levelet henger igjen slik at jeg får en best mulig opplevelse som spiller."*

*"Som spiller trenger jeg at fiendene forsvinner når jeg dreper dem slik at jeg kan se at jeg har drept dem og vite at jeg kan gå videre."

Fjerne collision bokser når levelet byttes.

Fjern collision bokser når fiender dør.

# Produkt og kode

**Fikset siden sist**

* Lagt til dokumentasjon
* Utbedret testing
* Laget Factory for level

* Klasse diagram:

![Class Diagram](./Doxygraph_Oblig4.png?raw=true "Klasse Diagram")
