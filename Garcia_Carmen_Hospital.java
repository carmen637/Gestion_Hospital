import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class Garcia_Carmen_Hospital {
    //Creamos los HashMap y PriorityQueue para registrar el nivel de triaje de los pacientes. 
    public static HashMap<String, Paciente> pacientes = new HashMap<>(); 
    public static HashMap<String, PersonalMedico> medicos = new HashMap<>(); 
    public static PriorityQueue<Paciente> prioridad = new PriorityQueue<>();
    public static void main(String[] args) {
        //Creamos objetos de la clase Personal Medico
        PersonalMedico medico1 = new PersonalMedico("Juan", "disponible", new ArrayList<>());
        PersonalMedico medico2 = new PersonalMedico("Manuel", "ocupado", new ArrayList<>());
        //Añadimos los medicos al HashMap de medicos
        medicos.put(medico1.getNombreMedico(), medico1);
        medicos.put(medico2.getNombreMedico(), medico2);
        
        Scanner teclado = new Scanner(System.in);
        int opcion;
        //Menú interactivo
        do{
            System.out.println("1- Registrar al paciente");
            System.out.println("2- Registrar motivo de la consulta");
            System.out.println("3- Asignar número único de atención");
            System.out.println("4- Verificar historial");
            System.out.println("5- Información del personal médico");
            System.out.println("6- Asignar paciente a medico");
            System.out.println("7- Atender paciente");
            System.out.println("0- Salir");

            System.out.println("Elige una opción");
            opcion = Integer.parseInt(teclado.nextLine());
            //Definimos las funciones para cada opción
            switch (opcion) {
                case 1:
                    registrarPaciente(teclado);
                    break;
                case 2:
                    registrarMotivoConsulta(teclado);
                    break;
                case 3:
                    asignarNumeroAtencion(teclado);
                    break;
                case 4:
                    verificarHistorial(teclado);
                    break;
                case 5:
                    informacionPersonal();
                    break;
                case 6:
                    System.out.println("Escribe el dni del paciente");
                    String dni = teclado.nextLine();
                    Paciente paciente = pacientes.get(dni);
                    if (paciente != null) {
                        asignarPacienteAMedico(paciente);
                    } else {
                        System.out.println("Paciente no encontrado en la base de datos");
                    }
                    break;
                case 7:
                    atenderPaciente();
                    break;
            }
            
        } while(opcion !=0);
        
    }
    public static void registrarPaciente(Scanner teclado){
        System.out.println("Escribe el dni: ");
        String dni = teclado.nextLine();

        System.out.println("Escribe el nombre del paciente: ");
        String nombre = teclado.nextLine();

        System.out.println("Escribe la edad del paciente: ");
        int edad = Integer.parseInt(teclado.nextLine());

        System.out.println("Escribe el sexo del paciente: ");
        String sexo = teclado.nextLine();

        System.out.println("Introduce el teléfono del paciente: ");
        String telefono = teclado.nextLine();

        System.out.println("Introduce la direccion del paciente");
        String direccion = teclado.nextLine();

        //Después de registrar los datos, creamos un nuevo objeto paciente para guardarlo en el HashMap de pacientes
        Paciente paciente = new Paciente(dni, nombre, edad, sexo, telefono, direccion);
        pacientes.put(dni, paciente);
        System.out.println("Paciente registrado");
    }
    public static void registrarMotivoConsulta(Scanner teclado){

        System.out.println("Escribe el dni del paciente");
        String dni = teclado.nextLine();
        Paciente paciente = pacientes.get(dni); //Busco al paciente obteniendo su dni del HashMap
        if (paciente != null) { //Si el dni ya se registro anteriormente, procede a preguntar motivo, sitomas, etc
            System.out.println("Escribe el motivo de la consulta");
            String motivo = teclado.nextLine();

            System.out.println("Cuantos síntomas presenta");
            int cantidadSintomas = Integer.parseInt(teclado.nextLine());
    
            System.out.println("Lista todos los síntomas que presenta");
            ArrayList<String> sintomas = new ArrayList<>();
            for(int i = 0; i < cantidadSintomas; i++){
                sintomas.add(teclado.nextLine());
            }
            System.out.println("Duración de los síntomas");
            String duracionSintoma = teclado.nextLine();
    
            System.out.println("Intensidad");
            int intensidad = Integer.parseInt(teclado.nextLine());

            System.out.println("Nivel de triaje: ");
            int triaje = Integer.parseInt(teclado.nextLine());

            //Al no haber inicializado estos datos en el constructor, recurro a los set e inicializo estos atributos con los valores que ha pasado el usuario
            paciente.setMotivo(motivo);
            paciente.setListaSintomas(sintomas);
            paciente.setDuracionSintoma(duracionSintoma);
            paciente.setIntensidad(intensidad);
            paciente.setTriaje(triaje);
            //Añado al paciente a la cola de prioridad
            prioridad.add(paciente);
            
            System.out.println("Paciente añadido a la cola");
        }
         else {
            System.out.println("Paciente no encontrado");
        }
       
        
    }
    public static void asignarNumeroAtencion(Scanner teclado){
        System.out.println("Escriba el dni del paciente");
        String dni = teclado.nextLine();
        Paciente paciente = pacientes.get(dni);

        if (paciente != null) {
            Random r = new Random(); //Asigno aleatoriamente su número de atención
            int numeroAleatorio = r.nextInt(10000);
            paciente.setNumeroAtencion(numeroAleatorio);
            System.out.println("El numero de atencion es: " + paciente.getNumeroAtencion());
        } else {
            System.out.println("Paciente no encontrado");
        }
    }
    public static void verificarHistorial(Scanner teclado){
        System.out.println("Escriba el dni del paciente");
        String dni = teclado.nextLine();
        Paciente paciente = pacientes.get(dni);

        if (paciente != null) {
            System.out.println("Historial del paciente: " + paciente);
        } else {
            System.out.println("No existe ese paciente en la base de datos");
        }

    }

    public static void informacionPersonal(){
        if (medicos.isEmpty()) { //Comprobamos si el HashMap de médicos está vacio
            System.out.println("No hay médicos");
        } else {
            for(String entrada : medicos.keySet()){ //Si no, recorremos el HashMap. keySet devuelve todos los nombres de los mñedicos, que sería la clave del HashMap medicos
                PersonalMedico medico = medicos.get(entrada); //Obtenemos los valores asociados a cada clave para obtener nombre, estado y su lista
                System.out.println("Nombre del médico: " + medico.getNombreMedico());
                System.out.println("Estado: " + medico.getEstado());
                System.out.println("Pacientes asignados: " + medico.getPacientesAsignados());
            }
        }
    }
    public static void asignarPacienteAMedico(Paciente paciente){
        if (paciente == null) {
            System.out.println("No hay pacientes en la lista");
        }
        for(PersonalMedico medico :medicos.values()){ //Recorremos el hashMap de medicos para buscar los medicos que aparezcan como disponibles
            if (medico.getEstado().equals("disponible")) { //Si obtenemos el estado y es igual a disponible, añadimos al paciente a la lista de clientes del medico disponible
                medico.getPacientesAsignados().add(paciente);
                medico.setEstado("ocupado"); //Una vez añadido, obtenemos el dato para cambiarlo a ocupado
                System.out.println("Paciente asignado al medico: " + medico.getNombreMedico());
            }
        }
    }
    public static void atenderPaciente() {
        if (!prioridad.isEmpty()) {
            Paciente pacienteAtendido = prioridad.poll(); // Extrae el paciente con mayor prioridad de la cola prioridad. Creamos la variable pacienteAtendido para guardar al paciente extraido de la cola de priordad
            System.out.println("Atendiendo a " + pacienteAtendido.getNombre() + " con nivel de triaje " + pacienteAtendido.getTriaje());
        } else {
            System.out.println("No hay pacientes en la cola de prioridad.");
        }
    }

    }
    
//Clase Paiente y atributos. Para que funcione la cola de prioridad, usamos comparable para poder comparar el nivel de triaje de los pacientes
class Paciente implements Comparable<Paciente>{
    private String dni;
    private String nombre;
    private int edad;
    private String sexo;
    private String telefono;
    private String direccion;
    private String motivo;
    private int numeroAtencion;
    private String historial;
    private ArrayList<String> listaSintomas;
    private String duracionSintoma;
    private int intensidad;
    private int hora;
    private int triaje;
//Método constructor
    public Paciente(String dni, String nombre, int edad, String sexo, String telefono, String direccion) {
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
        this.telefono = telefono;
        this.direccion = direccion;
        
    }
//Getters y Setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getNumeroAtencion() {
        return numeroAtencion;
    }

    public void setNumeroAtencion(int numeroAtencion) {
        this.numeroAtencion = numeroAtencion;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public ArrayList<String> getListaSintomas() {
        return listaSintomas;
    }

    public void setListaSintomas(ArrayList<String> listaSintomas) {
        this.listaSintomas = listaSintomas;
    }

    public String getDuracionSintoma() {
        return duracionSintoma;
    }

    public void setDuracionSintoma(String duracionSintoma) {
        this.duracionSintoma = duracionSintoma;
    }

    public int getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(int intensidad) {
        this.intensidad = intensidad;
    }
    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }
    public int getTriaje() {
        return triaje;
    }

    public void setTriaje(int triaje) {
        this.triaje = triaje;
    }
//Metodo toString
    @Override
    public String toString() {
        return "Paciente [dni=" + dni + ", nombre=" + nombre + ", edad=" + edad + ", sexo=" + sexo + ", telefono="
                + telefono + ", direccion=" + direccion + ", motivo=" + motivo + ", numeroAtencion=" + numeroAtencion
                + ", historial=" + historial + ", listaSintomas=" + listaSintomas + ", duracionSintoma="
                + duracionSintoma + ", intensidad=" + intensidad + ", hora=" + hora + ", triaje=" + triaje + "]";
//Método compareTo que compara un paciente con el resto
    }
    @Override
    public int compareTo(Paciente otro) {
        return Integer.compare(this.triaje, otro.triaje);
    }
        
}
//Clase personalMedico
class PersonalMedico{
    private String nombreMedico;
    private String estado;
    private ArrayList<Paciente> pacientesAsignados;
//Método constructor
    public PersonalMedico(String nombreMedico, String estado, ArrayList<Paciente> pacientesAsignados) {
        this.nombreMedico = nombreMedico;
        this.estado = estado;
        this.pacientesAsignados = pacientesAsignados;
    }
//Getters y Setters
    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<Paciente> getPacientesAsignados() {
        return pacientesAsignados;
    }

    public void setPacientesAsignados(ArrayList<Paciente> pacientesAsignados) {
        this.pacientesAsignados = pacientesAsignados;
    }
//Método toString
    @Override
    public String toString() {
        return "PersonalMedico [nombreMedico=" + nombreMedico + ", estado=" + estado + ", pacientesAsignados="
                + pacientesAsignados + "]";
    }

}
