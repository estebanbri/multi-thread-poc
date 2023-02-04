# Programacion Multi Hilo

### Variable volatile
variable ***volatile*** = variable visible por todos los threads.
Tecnicamente: Al definirla como volatile impedimos que la variable se almacene localmente en cada cache del cpu que
esta ejecutando el hilo, en cambio dicha variable se almacena en memoria RAM. Con esto logramos que 
cualquier cambio de valor hecho a la variable por algun thread sea visible por los demas threads.
Nota: con booleanos puede ser que no notes la diferencia porque en tu ejemplo si vos le quitas la palabra volatile
al flag va a funcionar igual PERO puede ser que en alguna ejecucion no funcione, es decir te genere inconsistencias
con volatile te aseguras que siempre pero siempre va a funcionar. El ejemplo mas aplicable a volatile es hacer un contador
que sea incrementado por dos threads simultaneamente si no usas volatile va a generar inconsistencias en el contador,
es decir un thhread no va a ver los ultimos cambios del thhread anterior, con volatile si va a funcionar correctamente.

### ¿Es necesario usar volatile cuando uso "bloques/metodos synchronized", o cuando uso  otros mecanismos de sincronizacion como por ej: ReentrantLock"
Fuente 1:
- No, both methods are defined with the synchronized keyword this means that they will never be executed at the same time 
and also that the memory will be synchronized. 
- volatile is never needed for variables that are accessed inside a synchronized block.
- If we would use other synchronization mechanisms provided by Java, 
for example ReentrantReadWriteLock instead of synchronized we also would not need volatile, 
because properly used locks have the same memory guarantees.

Fuente 2:
- The use of volatile in synchronized blocks or Reentrant Locks is not necessary because these mechanisms provide sufficient synchronization guarantees for controlling access to shared data.
- In synchronized blocks, only one thread can enter the block at a time, ensuring that data shared within the block is accessed by only one thread at a time and prevents race conditions.
- Reentrant Locks, on the other hand, also provide synchronization guarantees and allow for more fine-grained control over the locking and unlocking of shared data, ensuring that data is accessed in a thread-safe manner.
- Thus, the use of volatile is not needed in these cases, as the underlying mechanism provides the necessary synchronization guarantees.

En resumen no necesitas usar variables 'volatile' cuando ya estas usando synchronize o locks para sincronizar.

### Diferencia entre ScheduleWithFixedRate  versus ScheduleWithFixedDelay
***ScheduleWithFixedRate***, la siguiente task va a comenzar:
- inmediatamente si a la task actual le llevo mas tiempo de ejecucion que el delay definido
de ejecucion que el definido por el delay.
- cuando se complete el delay.
```
start-------------[execution time]++++++[execution time+++++++][ execution time]  
    | init delay  |              | idle |              | extra||               |          
```
Resumen: aplica delay dependiendo del tiempo de ejecucion de las task, si task demora menos se queda idle esperando el 
delay, si la ejecucion de la task se paso en tiempo del delay la siguiente task comienza inmediatamente.

***ScheduleWithFixedDelay*** la siguiente task va comenzar:
- cuando se complete el delay contando a partir del tiempo de finalizacion de la task actual.
```
start-------------[execution time]-------[execution time+++++++]--------[ execution time]  
    | init delay  |              | delay |                     | delay  |               |
```
Resumen: aplica delay fijo sin importar duracion de las task

#### Ejemplo de ScheduleWithFixedRate con delay de 1 hora:
- 00:00: Start making coffee
- 00:10: Finish making coffee
- 01:00: Start making coffee
- 01:10: Finish making coffee
- 02:00: Start making coffee
- 02:10: Finish making coffee

#### Ejemplo de ScheduleWithFixedDelay con delay de 1 hora:
- 00:00: Start making coffee
- 00:10: Finish making coffee
- 01:10: Start making coffee
- 01:20: Finish making coffee
- 02:20: Start making coffee
- 02:30: Finish making coffee



