### PROGETTO DI PROGRAMMAZIONE AD OGGETTI A.A. 2021-2022
# ANCYBERNETICS DIAGNOSTIC TOOL 
### *Strumento di diagnostica per dispositivi marini per ricezione dei dati di bordo e valutazione della situazione meteorologica marina*

### INDICE

## INTRODUZIONE (SILVER)

### Contesto

Il progetto è stato proposto come frutto della collaborazione con uno spinoff accademico i cui sviluppatori sono soci.
Quest'ultimo è chiamato **ANcybernetics S.r.l.** e si occupa dello sviluppo di un robot pesciformi a servizio dell'istruzione e della ricerca.

### Obiettivo 🎯

In un ipotetico futuro in cui i dispositivi prodotti possano navigare autonomamente, si è reso necessario un sistema di controllo e diagnostica capace di monitorare i parametri principali di ogni robot: il progetto sviluppato si presta come soluzione ideale a questa problematica.

### Descrizione

Il progetto è un'applicazione Springboot scritta in linguaggio **Java**,  che consente di:
- raccogliere i vari dati di diagnostica e le coordinate geografiche da ogni dispositivo in circolazione
- ordinare i dati in un database e memorizzarli in locale tramite un servizio di datalogging
- raccogliere dal web i dati di previsione meteorologica relativi alle coordinate dei dispositivi 
- rendere disponibile all'utente la visualizzazione di questi dati attraverso varie modalità, permettendo di filtrarli o di eseguire statistiche su di essi.

## COMPONENTI E STRUMENTI UTILIZZATI
Per un'approccio concettuale più concreto, di seguito si descrivono i macroblocchi hardware e software utilizzati durante lo sviluppo del progetto, partendo dalla sorgenti di dati per arrivare all'utente.

[SCHEMA A BLOCCHI SUL PROGETTO?]

### Robot marino (SILVER)

Per aggiungere un grado di difficoltà al progetto, si è supposto che l'applicativo comunichi con dispositivi appartenenti a tre ipotetiche versioni, che si differenziano per la sensoristica di bordo.
| Versione | Sensoristica al suo interno |
| ------ | ------ |
| ANcyb Ver_G| [plugins/dropbox/README.md][PlDb] |
| GitHub | [plugins/github/README.md][PlGh] |
| Google Drive | [plugins/googledrive/README.md][PlGd] |
| OneDrive | [plugins/onedrive/README.md][PlOd] |




Esso è composto principalmente da:
- un sistema di propulsione motorizzata
- uno scomparto hardware

In particolare, quest'ultimo è stato ideato per ospitare il microcontrollore e della sensoristica, ad esempio un GPS e un termometro.
Abbiamo intenzione di scrivere una routine in micropython nel microcontrollore di bordo che (via WiFi) esso manda i dati ad un servizio remoto: 
successivamente, un client Java prende la posizione del robot e la inserisce in una mappa, indicandone la temperatura.


### REST API (SILVER)

Il servizio di previsione meteorologica marina è fornito dall'API Storm Glass. 
Questo servizio si distingue dagli altri simili per la praticità d'uso e l'attendibilità:
- Effettuando la chiamata con un'unica rotta e inserendo le coordinate come parametri, è possibile ricevere 23 tipi di dato differenti sulla situazione meteorologica.

### Interfaccia utente (SILVER)

L'interfaccia utente 

### SERVIZIO MQTT (JACK)



## ROTTE (SILVER)

## UML (SILVER)

## DIMOSTRAZIONE DI FUNZIONAMENTO (JACK)

## ECCEZIONI (JACK)

## TEST (JACK)

## EVENTUALI SVILUPPI FUTURI (JACK)

A causa degli scopi prettamente didattici e le tempistiche relativamente ridotte per un progetto reale di tali dimensioni, 
il lavoro ha subito varie semplificazioni funzionali che ne giustificano la reale implementazione.

Di seguito vengono elencate alcune features che avrebbero conferito al lavoro una effettiva utilità in campo:

- Sostituzione del sistema di posizionamento subacqueo con tecnologia USBL al posto di quello GPS (per permettere l'invio dei dati in immersione)
- ....

##AUTORI



