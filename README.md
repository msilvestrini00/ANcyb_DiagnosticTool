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

##### Descrizione

Il servizio di previsione meteorologica marina è fornito dalla REST API [Storm Glass](https://rapidapi.com/ManniskaMaskin/api/storm-glass/). 

Essa si distingue dalle altre simili per la praticità d'uso e l'attendibilità:
- I dati si estendono fino a sette giorni successivi da quello corrente, con risoluzione oraria
- Il servizio è disponibile tramite un'unica rotta, che prende come parametri le coordinate di cui si cercano le previsioni 
- E' possibile ottenere 23 tipi di dato differenti sulla situazione meteorologica
- I dati ricevuti derivano da molteplici sorgenti

##### Utilizzo

Dopo essersi iscritti al sito si riceve l'API key, tramite la quale è possibile utilizzare la rotta scritta in questo modo:

` https://stormglass.p.rapidapi.com/forecast?rapidapi-key={ APIkey }&lat={ latitudine }&lng={ longitudine } `

[SCREEN POSTMAN CON ROTTA E PARAMETRI?]

Latitudine e longitudine vanno scritte in formato **Gradi decimali (DDD)**: un esempio è ` 41.890218, 12.492434 `

Una volta richiamata la rotta, si riceve una struttura di oggetti JSON che contengono i dati meteorologici.

##### Metadati

A seguito di una chiamata eseguita il giorno 19 Gennaio, si ricevono i seguenti metadati:

```json
"meta": {
        "end": "2022-01-29 00:00",
        "lat": 43.57517,
        "lng": 13.587715,
        "params": [
            "waterTemperature",
            "wavePeriod",
            "waveDirection",
            "waveDirection",
            "waveHeight",
            "windWaveDirection",
            "windWaveHeight",
            "windWavePeriod",
            "swellPeriod",
            "swellDirection",
            "swellHeight",
            "windSpeed",
            "windDirection",
            "airTemperature",
            "precipitation",
            "gust",
            "cloudCover",
            "humidity",
            "pressure",
            "visibility",
            "seaLevel",
            "currentSpeed",
            "currentDirection"
        ],
        "start": "2022-01-19 00:00"
    }
```
[FARE IL CODE BLOCK SCROLLABILE?]

*ATTENZIONE: i metadati riportati sopra dichiarano che le previsioni disponibili si estendono per dieci giorni, ma per motivi di attendibilità dei dati il servizio è stato ristretto a sette.*

___
### SERVIZIO MQTT (JACK)

Lo scambio dei dati tra applicativo e dispositivi avviene tramite protocollo di comunicazione **MQTT**.

Viste le condizioni di lavoro e la finalità didattica del progetto è stato sfruttato come broker un server chiamato [MQTTHQ](https://mqtthq.com/): un webserver online pubblico destinato proprio a test di sistemi IoT. Il broker offre inoltre un utile [client](https://mqtthq.com/client) integrato che permette di gestire e simulare subscribe e publish (le funzionalità sono meglio descritte tutorial).

I vari dispositivi sottomarini effettuano regolarmente dei publish al topic "ANcybDiagnosticTool" tramite i quali inviano messaggi che rispettano una certa sintassi compresa poi dell'applicativo che poi effettua l'opportuna modellazione (vedi dataManager).

L'applicativo è in costante ascolto grazie al subscribe allo stesso topic "ANcybDiagnosticTool" e così riceve i messaggi pubblicati sul topic da ciascun dispositivo.

Il subscribe viene configurato nel `main` del programma tramite il costruttore del MQTT client.
```java
ANcybMqttClient mqttClient = new ANcybMqttClient();
```
```java
package it.univpm.ancyb_diagnosticTool.mqtt.mqttClient;

public ANcybMqttClient() throws MqttException {
        ANcybMqttClient.getInstance();
        ancybDataManager = new ANcybDataManager();
        dataLog = new DataLogger();
        this.subscribe("ANcybDiagnosticTool");
    }
```
Di seguito le configurazioni dei client:
| Proprietà | ancybDiagnostiTool | dipositivi |
| ------ | ------ | ------ |
| ClientID | spring-server-ancyb-(data e ora all'avvio) | ancybFish-(MAC address) |
| keepalive |  | 300 |

**NOTA:** *il publish dell'applicativo non è stato implementato per motivi di sintesi del progetto.
Per una comunicazione da applicativo a dispositivo sarebbe stato possibile sfruttare un topic personalizzato in base all'indirizzo MAC (in quanto univoco) per ciascun robot.
```
ANcybDiagnosticTool/a4:cf:12:76:76:95
```
___

### Interfaccia utente (SILVER)

Il progetto può essere sfruttato tramite un qualsiasi browser, ma in fase di testing e di sviluppo è risultato ottimo l'utilizzo di [Postman](https://www.postman.com/).
Quest'ultimo è uno strumento di testing di API che possiede numerose features, tra cui la possibilità di salvare le rotte chiamate e di tabulare i dati in ricezione.
Pertanto, almeno in una fase primaria è consigliato l'utilizzo di questo software.

___

## ROTTE (SILVER)

Di seguito sono elencate le rotte disponibili corredate delle relative descrizioni.
Alcune note comuni:
- Per ovvi motivi progettuali, le rotte sono fortemente legate ai robot marini. Per questo motivo esse si basano sull'indirizzo MAC del dispositivo selezionato.
- Al fine di normalizzare i dati in uscita dall'applicativo, tutte le rotte restituiscono un oggetto JSON.

| | Tipo | Rotta | Descrizione
----- | ------------ | -------------------- | ----------------------
1 | ` GET ` | `/tweet/metadata` | restituisce un JSONObject contenente le informazioni relative ai tipi di dato visualizzabili
2 | ` POST ` | `/tweet/get/{method}` | consente di fare la ricerca e salvare i dati e restituisce un messaggio di avvenuto salvataggio
3 | ` POST ` | `/tweet/data` | restituisce un JSONObject contenente i dati relativi ai tweet precedentemente salvati
4 | ` POST ` | `/tweet/filter/day` | restituisce un JSONObject contenente i tweet postati nel giorno inserito
5 | ` POST ` | `/tweet/filter/geo` | restituisce un JSONObject contenente i tweet postati dal luogo inserito
6 | ` POST ` | `/tweet/stats/day` | restituisce una HashMap con il numero di tweet postati nel giorno inserito e nei due precedenti

___

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
__

##AUTORI



