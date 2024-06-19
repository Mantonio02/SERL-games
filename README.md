# INF112 Project

* *SERL games*: *Tyra Fosheim Eide, Sebastian Refsnes, Jonathan Ski og Mathias Antonio Løland*
* [Blog](https://smuch12.github.io/INF112-Bloggpost/)

## Om spillet

A strange virus has spread across the world and infected everything from the wildlife to you.

But this curse gives you a strange gift - when you die, you are brought back to life by the virus who needs its host.

The people of the world needs your help to defeat this virus once and for all - but what will that do to you?

## Kjøring
* Prosjektet kan kjøres direkte med `mvn compile exec:java`
* Prosjektet testes med `mvn test`
* Prosjektet bygges med `mvn package`, etter dette kan prosjektet kjøres med `java -jar target/game-1.0-SNAPSHOT-fat.jar`
* Krever Java 15 eller senere

## Keybindings:
- Attack: **Space**
- Walk : **Arrow Keys**
- Run : **Left shift**
- Interact: **E**
- Open Shop: **X**
- Consume Item **C**
- Reset when dead: **R**
- Hotbar index : **1-9**
- Hotbar cycle : **Scroll Wheel**

## Kjente feil

Spilleren kan gå under hotbaren.

## Credits
- Lyd knyttet til bossen er hentet fra `https://pixabay.com/` under **CC0 License** lisensen.
- [Wolf attack lyd](https://assets.mixkit.co/active_storage/sfx/1773/1773.mp3) Faller under [MixKit Lisens](https://mixkit.co/license/#sfxFree) som tillater gratis kommersiell og privat bruk 
- All grafikk er tegnet av Tyra Fosheim Eide.
