### PROGETTO DI PROGRAMMAZIONE AD OGGETTI A.A. 2021-2022
# ANCYBERNETICS DIAGNOSTIC TOOL 
### *Strumento di diagnostica per dispositivi marini per ricezione dei dati di bordo e valutazione della situazione meteorologica marina*

### INDICE

## INTRODUZIONE 

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

### Robot marino

Per aggiungere un grado di difficoltà al progetto, si è supposto che l'applicativo comunichi con dispositivi appartenenti a tre ipotetiche versioni, che si differenziano leggermente per l'hardware al loro interno.

Tutti i robot sono composti principalmente da:
- un sistema di propulsione motorizzata
- uno scomparto hardware, caratterizzato da un microcontrollore e la sensoristica

Il dispositivo è dotato di un microcontrollore ESP32, il quale viene programmato tramite [MicroPython](https://micropython.org/).

Invece, per quanto riguarda i sensori sfruttati:
| Versione | Sensoristica  |
| ------ | ------ |
| ANcybFish Ver_G  | GPS         |
| ANcybFish Ver_P  | Pressione   |
| ANcybFish Ver_GT | GPS + Temperatura |

L'intero hardware è costituito da schede modulabili: questo lo rende estremamente facile da montare e/o intercambiare all'interno dello scomparto del dispositivo.

[FOTO VARIE?]

### REST API (SILVER)

Il servizio di previsione meteorologica marina è fornito dall'API Storm Glass. 
Questo servizio si distingue dagli altri simili per la praticità d'uso e l'attendibilità:
- Effettuando la chiamata con un'unica rotta e inserendo le coordinate come parametri, è possibile ricevere 23 tipi di dato differenti sulla situazione meteorologica.

### Interfaccia utente (SILVER)

L'interfaccia utente 

___
### SERVIZIO MQTT (JACK)

Lo scambio dei dati tra applicativo e dispositivi avviene tramite protocollo di comunicazione MQTT.
Viste le condizioni di lavoro e la finalità didattica del progetto è stato sfruttato come broker un server chiamato MQTTHQ  "https://mqtthq.com/": un webserver online pubblico destinato proprio a test di sistemi IoT. Il broker offre inoltre un utile client "https://mqtthq.com/client" integrato che permette di gestire e simulare subscribe e publish (le funzionalità sono meglio descritte tutorial).
I vari dispositivi sottomarini effettuano regolarmente dei publish al topic "ANcybDiagnosticTool" tramite i quali inviano messaggi che rispettano una certa sintassi compresa poi dell'applicativo che poi effettua l'opportuna modellazione (vedi dataManager).
L'applicativo è in costante ascolto grazie al subscribe allo stesso topic "ANcybDiagnosticTool" e così riceve i messaggi pubblicati sul topic da ciascun dispositivo.

Di seguito le configurazioni dei client:
|Proprietà|ancybDiagnostiTool|dipositivi|
|ClientID|spring-server-ancyb-(data e ora all'avvio)|ancybFish-(MAC address)|
|keepalive|||


**NOTA:** *il publish dell'applicativo non è stato implementato per motivi di sintesi del progetto.
Per una comunicazione da applicativo a dispositivo sarebbe stato possibile sfruttare un topic personalizzato in base all'indirizzo MAC (in quanto univoco) per ciascun robot.
```
ANcybDiagnosticTool/a4:cf:12:76:76:95
```
___

## ROTTE (SILVER)

## UML (SILVER)

___
## DIMOSTRAZIONE DI FUNZIONAMENTO (JACK)



___
## ECCEZIONI (JACK)



___
## TEST (JACK)



___
## EVENTUALI SVILUPPI FUTURI (JACK)

A causa degli scopi prettamente didattici e le tempistiche relativamente ridotte per un progetto reale di tali dimensioni, 
il lavoro ha subito varie semplificazioni funzionali che ne giustificano la reale implementazione.

Di seguito vengono elencate alcune features che avrebbero conferito al lavoro una effettiva utilità in campo:

- Sostituzione del sistema di posizionamento subacqueo con tecnologia USBL al posto di quello GPS (per permettere l'invio dei dati in immersione)
- Implementazione di un'interfaccia web che rende l'applicativo più user-friendly:
    - integrando Google Maps per visualizzare graficamente la posizione dei dispositivi
    - visualizzando i dati descritti sopra in un'unica schermata
    - trasformando l'imput da testuale (inserimento di rotte) a grafico (pulsanti)

##AUTORI



