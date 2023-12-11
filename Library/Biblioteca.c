#include <stdio.h>
#include <stdlib.h>
#include<devutil.h>

#define LENISBN 14
#define LENTITULO 30
#define LENEDIT 25
#define LENIDAUTOR 11
#define LENNOMB 41
#define LENAPEL 36
#define LENIDDOC 21
#define LENTELEFONO 11

#define ARCHUS		"datos\\usuarios.dat"
#define ARCHLIBS	"datos\\libros.dat"
#define ARCHAUT	"datos\\autor.dat"
#define ARCHPRES	"datos\\prestamos.dat"

typedef struct{
	unsigned short int dd, mm, yyy;
}FECHA;


typedef struct{
	char ISBN[LENISBN];
	char titulo[LENTITULO];
	char editora[LENEDIT];
	char idautor[LENIDAUTOR];
	FECHA fechaedicion;
	int edicion;
}LIBRO;


typedef struct{
char idautor[LENIDAUTOR];
char nombres[LENNOMB];
char apellidos[LENAPEL];
FECHA fechanacimiento;
}AUTOR;


typedef struct{
	char iddocumento[LENIDDOC];
	char telefono[LENTELEFONO];
	char nombres[LENNOMB];
	char apellidos[LENAPEL];
}USUARIO;


typedef struct{
	char iddocumento[LENIDDOC];
	char ISBN[LENISBN];
	FECHA fecprestamo;
	FECHA fecdevolucion;
	int diasprestamo;
	float montoprestamo;
	float recargodiario;
}PRESTAMOS;

typedef struct nodo{
   USUARIO	user;
   struct nodo *sig,*ant;
}NODOUS;

typedef struct nodo2{
	AUTOR aut;
   struct nodo2 *sig,*ant;
}NODOAUT;

typedef struct nodo3{
	LIBRO lib;
	struct nodo3 *sig, *ant;
}NODOLIB;

typedef struct nodo4{
	PRESTAMOS pres;
	struct nodo4 *sig, *ant;
}NODOPRES;

AUTOR capautor(int, int);
void freeaut(NODOAUT *);
void listarautor(NODOAUT *);
void insertnodoinautor(NODOAUT **,NODOAUT **,AUTOR);
void InsertarColaListautor(NODOAUT **,NODOAUT **, AUTOR);
void salvarautor(NODOAUT *);
int EliminarAutor(NODOAUT **,NODOLIB**,NODOAUT *);
int cantmaaut(NODOAUT **,int);

USUARIO capuser(int);
void listar(NODOUS *);
void freeus(NODOUS *);
void salvaruser(NODOUS *);
void insertnodoinuser(NODOUS **,NODOUS **,USUARIO);
void InsertarColaListauser(NODOUS **,NODOUS **, USUARIO);
int EliminarUser(NODOUS **,NODOPRES **,NODOUS *);
int cantma(NODOUS **,int);

LIBRO caplib(NODOAUT **,NODOAUT *,int);
void listarlib(NODOLIB *);
void freelib(NODOLIB *);
void insertnodoinlib(NODOLIB **,NODOLIB **,LIBRO);
void InsertarColaListlib(NODOLIB **,NODOLIB **, LIBRO);
int EliminarLibro(NODOLIB **,NODOPRES**,NODOLIB *);
void salvarlib(NODOLIB *);

PRESTAMOS cappres(NODOUS **,NODOPRES **,NODOUS *,NODOLIB **, NODOLIB *,char *,int);
void listarpres(NODOPRES *);
void salvarpres(NODOPRES *);
void insertnodoinpres(NODOPRES **,NODOPRES **,PRESTAMOS);
void InsertarColaListpres(NODOPRES **,NODOPRES **, PRESTAMOS);
void devolverpres(NODOPRES **,NODOPRES *);
void freepres(NODOPRES *);

USUARIO moduser(NODOUS **,NODOUS *,int);

AUTOR modautor(NODOAUT **,NODOAUT *,int,int);

LIBRO modlib(NODOLIB *,NODOAUT *, int);

PRESTAMOS modpres(NODOUS **,NODOPRES **,NODOUS *,NODOLIB **, NODOLIB *,char *,int);

void seleccion(int, int);
void eliminar(int,int,int, int);
void usererror();
void autorerror();
void libroerror();

int main(){
	FILE *pf;
	NODOUS *cabeza, *cola;
   cabeza = cola = NULL;
	NODOAUT *cab, *col;
	cab = col = NULL;
	NODOLIB *cablib, *colalib;
	cablib = colalib = NULL;
	NODOPRES *cabpres, *colapres;
	cabpres = colapres = NULL;
	USUARIO user;
	AUTOR aut;
	LIBRO lib;
	PRESTAMOS pres;
	time_t t;
	struct tm *tm ;

	char menu[][TAMAOPC] ={"Libros    ", "Autores   ", "Usuarios  ", "Prestamos ", "Salir     "};
	char menulib[][TAMAOPC] = {"Ingresar  ","Modificar ","Listar    ","Eliminar  ","Salvar    ","Salir     "};
	char menupresta[][TAMAOPC] = {"Prestar   ","Modificar ","Listar    ","Devolver  ","Salvar    ","Salir     "};
	char pregunta[][TAMAOPC] ={  "  no   ", "  si   "};
   char tipregunta[TAMAOPC] = "Seguro?";
   char titulo[TAMAOPC] = "   Menu   ";
   int opcs = 5, opc = 0, opclib = 0, opcaut=0, us =0, opcus=0, au=0, pro =0,li=0,proau,opcpre=0, pr=0;

	char fecha[11],fecha2[11];

	t = time(NULL);

	tm = localtime(&t);
	
	strftime(fecha,sizeof(fecha), "%d/%m/%Y",tm);	

	int fechain = atoi(strncpy(fecha2,fecha+6,4));


   if ( (pf = fopen(ARCHUS,"rb")) != NULL ){
      while ( fread(&user,sizeof(USUARIO),1,pf) )
      {
         if ( us == 0 )
         {
            insertnodoinuser(&cabeza,&cola,user);
            cola = cabeza;
         }
         else
         {
            InsertarColaListauser(&cabeza,&cola,user);
         }
         us++;
      }
      fclose(pf);
	}

	if ( (pf = fopen(ARCHAUT,"rb")) != NULL ){
	   while ( fread(&aut,sizeof(AUTOR),1,pf) )
	   {
	      if ( au == 0 )
	      {
	         insertnodoinautor(&cab,&col,aut);
	         col = cab;
	      }
	      else
	      {
	         InsertarColaListautor(&cab,&col,aut);
	      }
	      au++;
	   }
	   fclose(pf);
	}

   if ( (pf = fopen(ARCHPRES,"rb")) != NULL ){
      while ( fread(&pres,sizeof(PRESTAMOS),1,pf) )
      {
         if ( pr == 0 )
         {
            insertnodoinpres(&cabpres,&colapres,pres);
            colapres = cabpres;
         }
         else
         {
            InsertarColaListpres(&cabpres,&colapres,pres);
         }
         pr++;
      }
      fclose(pf);
	}

   if ( (pf = fopen(ARCHLIBS,"rb")) != NULL ){
      while ( fread(&lib,sizeof(LIBRO),1,pf) )
      {
         if ( li == 0 )
         {
            insertnodoinlib(&cablib,&colalib,lib);
            colalib = cablib;
         }
         else
         {
            InsertarColaListlib(&cablib,&colalib,lib);
         }
         li++;
      }
      fclose(pf);
	}

   do{
      system("cls");
      opc = showmenu(menu,titulo,opcs,2,2,opc);
      switch(opc){
			case 0://Libros

				do{
	      		system("cls");
      			opclib = showmenu(menulib,"  Libros  ",6,2,2,opc);
					switch(opclib){
						case 0://Ingresar
							if ( li == 0 ){
								insertnodoinlib(&cablib,&colalib,caplib(&cab,cab,au));
								col = cab;
							}else{
								InsertarColaListlib(&cablib,&colalib,caplib(&cab,cab, au));
							}
					      li++;
							break;
						case 1:
							modlib(cablib,cab,5);
							break;
						case 2://Listar
							system("cls");
							listarlib(cablib);
							break;
						case 3://Eliminar
							EliminarLibro(&cablib,&cabpres,cablib);
							break;
						case 4:
							salvarlib(cablib);
							break;	

					}
				}while(opclib!= 5 && opclib != -1);
				
				break;
			case 1: //Autores
				do{
					proau = cantmaaut(&cab,au);
	      		system("cls");
      			opcaut = showmenu(menulib," Autores  ",6,2,2,0);
					switch(opcaut){
						case 0://ingresar
							if ( au == 0 ){
								insertnodoinautor(&cab,&col,capautor(proau,fechain));
								col = cab;
							}else{
								InsertarColaListautor(&cab,&col,capautor(proau,fechain));
							}
					      au++;
							break;
						case 1:
							modautor(&cab,cab,3,fechain);
							break;
						case 2://listar
							system("cls");
							listarautor(cab);
							break;
						case 3://Eliminar
							EliminarAutor(&cab,&cablib,cab);
							break;
						case 4://salvar
							salvarautor(cab);
							break;	
					}
				}while(opcaut!= 5 && opcaut != -1);					
				break;
			case 2: //Usuarios;
				do{
					pro = cantma(&cabeza,us);
	      		system("cls");
      			opcus = showmenu(menulib," Usuarios ",6,2,2,0);
					switch(opcus){
						case 0://ingresar
							if ( us == 0 ){
								insertnodoinuser(&cabeza,&cola,capuser(pro));
								cola = cabeza;
							}else{
								InsertarColaListauser(&cabeza,&cola,capuser(pro));
							}
					      us++;
							break;	
							case 1://Modificar
								moduser(&cabeza,cabeza,3);
								break;
						case 2://listar
							system("cls");
							listar(cabeza);
							break;
						case 3://Eliminar
							EliminarUser(&cabeza,&cabpres,cabeza);
							break;
						case 4://Salvar
							salvaruser(cabeza);
							break;
					}
				
				}while(opcus!= 5 && opcus != -1);			

				break;
			case 3: //Prestamo
				do{
	      		system("cls");
      			opcpre = showmenu(menupresta,"Prestamos ",6,2,2,0);
					switch(opcpre){
						case 0://ingresar
							if ( pr == 0 ){
								insertnodoinpres(&cabpres,&colapres,cappres(&cabeza,&cabpres,cabeza,&cablib,cablib,fecha,pr));
								colapres = cabpres;
							}else{
								InsertarColaListpres(&cabpres,&colapres,cappres(&cabeza,&cabpres,cabeza,&cablib,cablib,fecha,pr));
							}
					      pr++;
							break;	
							case 1://Modificar
								modpres(&cabeza,&cabpres,cabeza,&cablib,cablib,fecha,4);
								break;
						case 2://listar
							system("cls");
							listarpres(cabpres);
							break;
						case 3://Eliminar
						devolverpres(&cabpres,cabpres);
							break;
						case 4://Salvar
							salvarpres(cabpres);
							break;
					}
				
				}while(opcpre!= 5 && opcpre != -1);			
				
				break;	
			case 4: //Salir;
				freeus(cabeza);
				freeaut(cab);
				freelib(cablib);
				freepres(cabpres);
		}
	}while(opc !=4 && opc != -1);

	if(opc == -1){
		freeus(cabeza);
		freeaut(cab);
		freelib(cablib);
		freepres(cabpres);
	}
   system("cls");

	return 0;
}


/*
   Función: listar
   Argumentos: (NODOUS *) cabeza. nodo con los datos de la lista.  
   Objetivo:  mostar la lista de los ususarios.
   Retorno: Ninguno.
*/

void listar(NODOUS *cabeza){
   NODOUS *ind;
	//USUARIO user;
	int cont=0;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};
	int nom=5,apel=5;
   for (ind = cabeza; ind; ind = ind->sig){
		if(nom<strlen(ind->user.nombres)){
			nom = strlen(ind->user.nombres);
		}			
	}
   for (ind = cabeza; ind; ind = ind->sig){
		if(apel<strlen(ind->user.apellidos)){
			apel = strlen(ind->user.apellidos);
		}			
	}
	sprintf(titulolista,"%-*s %-*s %-*s %-*s",LENIDDOC,"ID",nom,"Nombres",apel,"Apellidos",LENTELEFONO,"Telefono");
   for (ind = cabeza; ind; ind = ind->sig){
		sprintf(lista[cont],"%-*s %-*s %-*s %-*s\n",LENIDDOC,ind->user.iddocumento,nom,ind->user.nombres,apel,
					ind->user.apellidos,LENTELEFONO,ind->user.telefono);
		cont++;
	}
	showmenu(lista,titulolista,cont,1,2,0);
}

/*
   Función: cantma
   Argumentos: (NODOUS *) cab nodo con los datos de la lista.  
					(int ) cant. cantidad de usuarios.
   Objetivo:  busca al usuario que tiene mayor ID.
   Retorno: mayor.
*/

int cantma(NODOUS **cab,int cant){

   NODOUS *indice,*anterior;	
	int valores[100]={0};
	int indl=0, cont=0;
   for ( indice = *cab; indice; anterior = indice, indice = indice->sig){
		valores[indl] =atoi(indice->user.iddocumento);
		indl++;
	}
	int mayor = valores[0];

   for ( indice = *cab; indice; anterior = indice, indice = indice->sig){
	
		if(mayor< valores[cont]){
			mayor = valores[cont];
		}
		cont++;
	}
	
	return mayor;
	
}

/*
   Función: cantmaaut
   Argumentos: (NODOAUT *) cab nodo con los datos de la lista
					del autor.  
					(int ) cant. cantidad de autores de la lista.
   Objetivo:  busca al autor que tiene mayor ID en la lista.
   Retorno: mayor.
*/

int cantmaaut(NODOAUT **cab,int cant){

   NODOAUT *indice,*anterior;	
	int valores[100]={0};
	int indl=0, cont=0;
   for ( indice = *cab; indice; anterior = indice, indice = indice->sig){
		valores[indl] =atoi(indice->aut.idautor);
		indl++;
	}
	int mayor = valores[0];

   for ( indice = *cab; indice; anterior = indice, indice = indice->sig){
	
		if(mayor< valores[cont]){
			mayor = valores[cont];
		}
		cont++;
	}
	
	return mayor;
	
}


/*
   Función: listarautor
   Argumentos: (NODOAUT *) cabeza. nodo con los datos de la lista del autor.  
   Objetivo:  mostar la lista de los los autores.
   Retorno: Ninguno.
*/

void listarautor(NODOAUT *cabeza){
   NODOAUT *ind;
	int cont=0;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};
	int nom=5,apel=5;

   for (ind = cabeza; ind; ind = ind->sig){
		if(nom<strlen(ind->aut.nombres)){
			nom = strlen(ind->aut.nombres);
		}			
	}
   for (ind = cabeza; ind; ind = ind->sig){
		if(apel<strlen(ind->aut.apellidos)){
			apel = strlen(ind->aut.apellidos);
		}			
	}
	sprintf(titulolista," %-*s %-*s %-*s %-*s ",LENIDAUTOR,"ID",nom,"Nombres",apel,"Apellidos",10,"Fec.Nacim");
   for (ind = cabeza; ind; ind = ind->sig){
		sprintf(lista[cont]," %-*s %-*s %-*s %02d/%02d/%04d \n",LENIDAUTOR,ind->aut.idautor,nom,ind->aut.nombres,apel,
					ind->aut.apellidos,ind->aut.fechanacimiento.dd,ind->aut.fechanacimiento.mm,ind->aut.fechanacimiento.yyy);
		cont++;
	}
	showmenu(lista,titulolista,cont,1,2,0);
}


/*
   Función: capuser
   Argumentos: (int ) n. el id que coninua al mayor.  
   Objetivo:  presentar campos y capturar usuarios.
   Retorno: user.
*/

USUARIO capuser(int n){
	
   USUARIO user;


	system("cls");
	printf("Id: ");
	memset(user.iddocumento , 0, LENIDDOC);	
   solonumint(user.iddocumento,LENIDDOC,8,1,n+1);
	
	printf("\nTelefono: ");
	memset(user.telefono, 0, LENTELEFONO);
	capnums(user.telefono, LENTELEFONO-1,10,2);


	printf("\nNombre: ");
	memset(user.nombres, 0, LENNOMB);	
   capfieldAlpha(user.nombres,LENNOMB,8,3);

	printf("\nApellido: ");
	memset(user.apellidos, 0, LENAPEL);	
   capfieldAlpha(user.apellidos,LENAPEL,10,4);
	

   return user;
}


/*
   Función: seleccion
   Argumentos: (int ) x. posicion en x.  
					(int ) y. posicion en y.
   Objetivo:  presentar una franja verde si se selecciona.
   Retorno: Ninguno.
*/

void seleccion(int x, int y){
	_setcursortype(0);
	gotoxy(x,y);
	setcolor(GR,GR);
	printf("k");
	colordefault();

	_setcursortype(100);
}


/*
   Función: eliminar
   Argumentos: (int ) x. posicion en x.  
					(int ) y. posicion en y.
					(int ) x2. posicion del cursor x.  
					(int ) y2. posicion del cursor y.
   Objetivo:  borrar la franja verde y devolver el cursor
					al inicio del campo.
   Retorno: Ninguno.
*/

void eliminar(int x, int y,int x2, int y2){

	gotoxy(x,y);
	setcolor(CTDEF,CFDEF);
	printf(" ");
	gotoxy(x2,y2);	

}


/*
   Función: moduser
   Argumentos: (NODOUS **) cabe. nodo con los datos de la lista.
					(NODOUS **) cabe. nodo con los datos de la lista.
					(int ) n. cantidad de campos a presentar.
   Objetivo:  mostrar la edicion de los ususarios.
   Retorno: user.
*/

USUARIO moduser(NODOUS **cab,NODOUS *cabe,int n){
	

	NODOUS *ind,*anterior;
	system("cls");
	int cont=0,opc, sub=0;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};
	char pregunta[TAMAOPC][TAMAOPC] ={  "  no    ", "  si    "};
   char tipregunta[TAMAOPC] = "Guardar?";
	char telefono[LENTELEFONO];
	char nombre[LENNOMB];
	char apellido[LENAPEL];
   char tecla;
   int salir = 1;
	int quest;
	int opcsel =0;
	int nom=5,apel=5;
   for (ind = cabe; ind; ind = ind->sig){
		if(nom<strlen(ind->user.nombres)){
			nom = strlen(ind->user.nombres);
		}			
	}
   for (ind = cabe; ind; ind = ind->sig){
		if(apel<strlen(ind->user.apellidos)){
			apel = strlen(ind->user.apellidos);
		}			
	}
	sprintf(titulolista,"%-*s %-*s %-*s %-*s",LENIDDOC,"ID",nom,"Nombres",apel,"Apellidos",LENTELEFONO,"Telefono");
   for (ind = cabe; ind; ind = ind->sig){
		sprintf(lista[cont],"%-*s %-*s %-*s %-*s\n",LENIDDOC,ind->user.iddocumento,nom,ind->user.nombres,apel,
					ind->user.apellidos,LENTELEFONO,ind->user.telefono);
		cont++;
	}
	opc = showmenu(lista,titulolista,cont,1,2,0);

   for ( ind = cabe; ind, sub<=opc; anterior = ind, ind = ind->sig)
   {
      if (sub==opc)
      {
		
			system("cls");
			printf("Id: ");
		   capf(ind->user.iddocumento,LENIDDOC,8,1);
			printf("\nTelefono: ");
			strcpy(telefono,ind->user.telefono);
			capf(telefono, LENTELEFONO-1,10,2);
			printf("\nNombre: ");
			strcpy(nombre,ind->user.nombres);
		   capf(nombre,LENNOMB,8,3);
			printf("\nApellido: ");
		
		   capf(ind->user.apellidos,LENAPEL,10,4);
			strcpy(apellido, ind->user.apellidos);
		   do{
		      //showopcxy(menu, titulo, n, px, py, opcsel);
				if(opcsel==0){
					seleccion(10+LENTELEFONO,2);
					capnums(telefono, LENTELEFONO-1,9,2);
					eliminar(10+LENTELEFONO,2,10,2);
				}else if(opcsel ==1){
					seleccion(8+LENNOMB+1,3);
				   capfieldAlpha(nombre,LENNOMB,8,3);
					eliminar(8+LENNOMB+1,3,8,3);
				}else if(opcsel == 2){
					seleccion(10+LENAPEL+1,4);
				   capfieldAlpha(apellido,LENAPEL,10,4);	
					eliminar(10+LENAPEL+1,4,10,4);
				}
		      do{
		         tecla = getch();
		      }while (tecla != ESC && tecla != ARRIBA && tecla != ABAJO);
		
		      if ( tecla != ESC )
		      {
		
		         if ( tecla == ARRIBA )
		         {
		            if ( opcsel != 0 )
		               opcsel --;
		            else
		               opcsel = n - 1;
		         }
		         if ( tecla == ABAJO )
		         {
		            if ( opcsel < n - 1 )
		               opcsel ++;
		            else
		               opcsel = 0;
		         }
		      }
		      else
		         opcsel = n - 1;
				
		   }while ( tecla != ESC );
			system("cls");
			quest = showmenu(pregunta,tipregunta,2,2,3,0);
			if(quest ==1){
				strcpy(ind->user.telefono, telefono);
				strcpy(ind->user.nombres, nombre);
				strcpy(ind->user.apellidos, apellido);
			}
      }
		sub++;
   }

}



/*
   Función: modautor
   Argumentos: (NODOAUT **) cab. nodo con los datos de la lista.
					(NODOAUT **) cabe. nodo con los datos de la lista.
					(int ) n. cantidad de campos a presentar.
					(int )fec. anio actual.
   Objetivo:  modificar los autores de la lista.
   Retorno: user.
*/

AUTOR modautor(NODOAUT **cab,NODOAUT *cabe,int n,int fec){
	
	AUTOR	aut;
	NODOAUT *ind,*anterior;
	AUTOR *au;
	system("cls");
	int cont=0,opc, sub=0;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};
	char pregunta[TAMAOPC][TAMAOPC] ={  "  no    ", "  si    "};
   char tipregunta[TAMAOPC] = "Guardar?";
	char nombre[LENNOMB]={0};
	char apellido[LENAPEL]={0};
	char campofecha[LENLINE] = "  /  /    ";
	int dia,mes,anio;
   char tecla ;
   int salir = 0;
	int quest;
	int opcsel =0;
	int nom=5,apel=5;

   for (ind = cabe; ind; ind = ind->sig){
		if(nom<strlen(ind->aut.nombres)){
			nom = strlen(ind->aut.nombres);
		}			
	}
   for (ind = cabe; ind; ind = ind->sig){
		if(apel<strlen(ind->aut.apellidos)){
			apel = strlen(ind->aut.apellidos);
		}			
	}
	sprintf(titulolista," %-*s %-*s %-*s %-*s ",LENIDAUTOR,"ID",nom,"Nombres",apel,"Apellidos",10,"Fec.Nacim");
   for (ind = cabe; ind; ind = ind->sig){
		sprintf(lista[cont]," %-*s %-*s %-*s %02d/%02d/%04d \n",LENIDAUTOR,ind->aut.idautor,nom,ind->aut.nombres,apel,
					ind->aut.apellidos,ind->aut.fechanacimiento.dd,ind->aut.fechanacimiento.mm,ind->aut.fechanacimiento.yyy);
		cont++;
	}
	opc = showmenu(lista,titulolista,cont,1,2,0);

   for ( ind = cabe; ind, sub<=opc; ind = ind->sig)
   {
      if (sub==opc)
      {
		
			system("cls");
			printf("Id: ");
		   capf(ind->aut.idautor,LENIDAUTOR,8,1);

			printf("\nNombres: ");
			strcpy(nombre,ind->aut.nombres);
			capf(ind->aut.nombres, LENNOMB,10,2);
		
			printf("\nApellidos: ");
			strcpy(apellido,ind->aut.apellidos);
		   capf(ind->aut.apellidos,LENAPEL,8,3);

			printf("\nFecha de nacimiento(dd/mm/yy): ");
			sprintf(campofecha,"%02d %02d %04d",ind->aut.fechanacimiento.dd,ind->aut.fechanacimiento.mm,ind->aut.fechanacimiento.yyy);
		   capf(campofecha,10,30,4);

		   do{
		      //showopcxy(menu, titulo, n, px, py, opcsel);
				if(opcsel==0){
					seleccion(10+LENNOMB+1,2);
					capfieldAlpha(nombre,LENNOMB,10,2);
					eliminar(10+LENNOMB+1,2,11,2);
				}else if(opcsel ==1){
					seleccion(8+LENAPEL+1,3);
					capfieldAlpha(apellido,LENAPEL,8,3);	
					eliminar(8+LENAPEL+1,3,9,3);
				}else if(opcsel == 2){
					seleccion(30+10+1,4);
					do{
						fechascat(campofecha, &dia, &mes, &anio, 10,30,4);
					}while(fec-anio<18);
					eliminar(30+10+1,4,31,4);
				}
		      do{
		         tecla = getch();
		      }while (tecla != ESC && tecla != ARRIBA && tecla != ABAJO);
		
		      if ( tecla != ESC )
		      {
		
		         if ( tecla == ARRIBA )
		         {
		            if ( opcsel != 0 )
		               opcsel --;
		            else
		               opcsel = n - 1;
		         }
		         if ( tecla == ABAJO )
		         {
		            if ( opcsel < n - 1 )
		               opcsel ++;
		            else
		               opcsel = 0;
		         }
		      }
				
		   }while ( tecla != ESC );
			system("cls");
			quest = showmenu(pregunta,tipregunta,2,1,2,0);
			if(quest ==1){
				strcpy(ind->aut.nombres, nombre);
				strcpy(ind->aut.apellidos, apellido);
				ind->aut.fechanacimiento.dd = dia;
				ind->aut.fechanacimiento.mm = mes;
				ind->aut.fechanacimiento.yyy = anio;
			}
      }
		sub++;
   }



}



/*
   Función: modlib
   Argumentos: (NODOLIB **) cabeza. nodo con los datos de la lista de libros.
					(NODOAUT **) cabe. nodo con los datos de la lista de autor.
					(int ) n. cantidad de campos a presentar.
   Objetivo:  mostrar la edicion de los libros.
   Retorno: li.
*/


LIBRO modlib(NODOLIB *cabeza,NODOAUT *cabe, int n){
	
   LIBRO li;
   NODOLIB *ind,*anterior;
	NODOAUT *indau, *anteau;
	AUTOR *aut;

	int dia,mes,anio;
	char campofecha[LENLINE] = "  /  /    ";
	char strdate[LENLINE] = "  /  /    ";
	int cont=0,cont2=0,opc,opc2, sub=0,sub2=0;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};
	char edi[6] = {0};
   char tecla;
   int salir = 1;
	int quest;
	int opcsel =0;
	int nom=5,apel=5;
	char isbn[LENISBN]={0};
	char editora[LENEDIT]={0};
	char titulo[LENTITULO]={0};
	char pregunta[TAMAOPC][TAMAOPC] ={  "  no    ", "  si    "};
   char tipregunta[TAMAOPC] = "Guardar?";
	system("cls");
	int isn=1, titulo2 = 1, editor =1, autor=1;
   for (ind = cabeza; ind; ind = ind->sig){
		if(isn < strlen(ind->lib.ISBN))
			isn = strlen(ind->lib.ISBN);
	}
   for (ind = cabeza; ind; ind = ind->sig){
		if(titulo2 < strlen(ind->lib.titulo))
			titulo2 = strlen(ind->lib.titulo);
	}
   for (ind = cabeza; ind; ind = ind->sig){
		if(editor < strlen(ind->lib.editora))
			editor = strlen(ind->lib.editora);
	}

	sprintf(titulolista,"%-*s %-*s %-*s %-*s %-*s %s",isn,"ISBN",titulo2,"Titulo",editor,"Editora",LENIDAUTOR,"IdAutor",10,"FechaEdi","Edicion ");
   for (ind = cabeza; ind; ind = ind->sig){
		sprintf(lista[cont],"%-*s %-*s %-*s %-*s %02d/%02d/%04d %5d   \n",isn,ind->lib.ISBN,titulo2,ind->lib.titulo,editor,ind->lib.editora
					,LENIDAUTOR,ind->lib.idautor,ind->lib.fechaedicion.dd,ind->lib.fechaedicion.mm,ind->lib.fechaedicion.yyy, ind->lib.edicion);
		cont++;
	}
	
	opc = showmenu(lista,titulolista,cont,1,2,0);
	

   for ( ind = cabeza; ind, sub<=opc; anterior = ind, ind = ind->sig)
   {
      if (sub==opc){
			system("cls");

		   for (indau = cabe; indau; indau = indau->sig){
				if(nom<strlen(indau->aut.nombres)){
					nom = strlen(indau->aut.nombres);
				}			
			}
		   for (indau = cabe; indau; indau = indau->sig){
				if(apel<strlen(indau->aut.apellidos)){
					apel = strlen(indau->aut.apellidos);
				}			
			}
			printf("Seleccione el autor");
			sprintf(titulolista," %-*s %-*s %-*s ",LENIDAUTOR,"ID",nom,"Nombres",apel,"Apellidos",10);
			for (indau = cabe; indau; indau = indau->sig){
				sprintf(lista[cont2]," %-*s %-*s %-*s \n",LENIDAUTOR,indau->aut.idautor,nom,indau->aut.nombres,apel,
							indau->aut.apellidos);
				cont2++;
			}
			opc2 = showmenu(lista,titulolista,cont2,1,2,0);
			
			for ( indau = cabe; indau, sub2<=opc2; anteau = indau, indau = indau->sig)
			{
			   if (sub2==opc2)
			   {
					strcpy(ind->lib.idautor, indau->aut.idautor);
			   }
				sub2++;
			}	
		
			system("cls");
			printf("ISBN: ");
			strcpy(isbn,ind->lib.ISBN);
			capf(ind->lib.ISBN,LENISBN,8,1);
		
			printf("\nTitulo: ");	
			strcpy(titulo,ind->lib.titulo);
		   capf(ind->lib.titulo,LENTITULO,8,2);
		
			printf("\nEditora: ");
			strcpy(editora,ind->lib.editora);
		   capf(ind->lib.editora,LENEDIT,8,3);
		
			printf("\nFecha de edicion (dd/mm/yy): ");
			sprintf(campofecha,"%02d/%02d/%04d",ind->lib.fechaedicion.dd,ind->lib.fechaedicion.mm,ind->lib.fechaedicion.yyy);
			capf(campofecha,11,35,4);
			
			printf("\nEdicion: ");
			sprintf(edi,"%d",ind->lib.edicion);
			capf(edi,5,8,5);
		   do{
		      //showopcxy(menu, titulo, n, px, py, opcsel);
				if(opcsel==0){
					seleccion(8+LENISBN+1,1);
					capfieldAlpha(isbn,LENISBN,8,1);
					eliminar(8+LENISBN+1,1,9,1);
				}else if(opcsel ==1){
					seleccion(8+LENTITULO+1,2);
					capfieldAlpha(titulo,LENTITULO,8,2);	
					eliminar(8+LENTITULO+1,2,9,2);
				}else if(opcsel==2){
					seleccion(8+LENEDIT+1,3);
					capfieldAlpha(editora,LENEDIT,8,3);	
					eliminar(8+LENEDIT+1,3,9,3);
				}else if(opcsel == 3){
					seleccion(35+10+1,4);
					fechascat(campofecha, &dia, &mes, &anio, 11,35,4);
					eliminar(35+10+1,4,36,4);
				}else if(opcsel==4){
					seleccion(8+6+1,5);
					capnums2(edi,6,8,5);	
					eliminar(8+6+1,5,9,5);
				}
		      do{
		         tecla = getch();
		      }while (tecla != ESC && tecla != ARRIBA && tecla != ABAJO);
		
		      if ( tecla != ESC )
		      {
		
		         if ( tecla == ARRIBA )
		         {
		            if ( opcsel != 0 )
		               opcsel --;
		            else
		               opcsel = n - 1;
		         }
		         if ( tecla == ABAJO )
		         {
		            if ( opcsel < n - 1 )
		               opcsel ++;
		            else
		               opcsel = 0;
		         }
		      }
		      else
		         opcsel = n - 1;
				
		   }while ( tecla != ESC );
			system("cls");
			quest = showmenu(pregunta,tipregunta,2,1,2,0);
			if(quest ==1){
				strcpy(ind->lib.ISBN, isbn);
				strcpy(ind->lib.titulo, titulo);
				strcpy(ind->lib.editora,editora);
				ind->lib.edicion = atoi(edi);
				ind->lib.fechaedicion.dd = atoi(strncpy(strdate,campofecha,2));
				ind->lib.fechaedicion.mm = atoi(strncpy(strdate,campofecha+3,2));
				ind->lib.fechaedicion.yyy = atoi(strncpy(strdate,campofecha+6,4));
			}
   	}
		sub++;
   }

   return li;

}


/*
   Función: capautor
   Argumentos:	(int ) n. cantidad de campos a presentar.
					(int ) fec. el anio actual. 
   Objetivo:  mostrar los campos para capturar el autor.
   Retorno: autor.
*/


AUTOR capautor(int n,int fec){
	
   AUTOR aut;
	int dia,mes,anio;
	char campofecha[LENLINE] = "  /  /    ";
	system("cls");
	printf("Id: ");
	memset(aut.idautor , 0, LENIDAUTOR);	
   solonumint(aut.idautor,LENIDAUTOR,8,1,n+1);

	printf("\nNombre: ");
	memset(aut.nombres, 0, LENNOMB);	
   capfieldAlpha(aut.nombres,LENNOMB,8,2);

	printf("\nApellido: ");
	memset(aut.apellidos, 0, LENAPEL);	
   capfieldAlpha(aut.apellidos,LENAPEL,10,3);	
	
	printf("\nFecha de nacimiento (dd/mm/yy): ");
	do{
		fechascat(campofecha, &dia, &mes, &anio, 10,35,4);
		aut.fechanacimiento.dd = dia;
		aut.fechanacimiento.mm = mes;
		aut.fechanacimiento.yyy = anio;
		sprintf(campofecha,"  /  /    ");
	}while(fec-aut.fechanacimiento.yyy<18);
   return aut;
}


/*
   Función: insertnodoinuser
   Argumentos: (NODOUS **) cab. nodo con los datos de la lista de ususario.
					(NODOUS **) cola. nodo con los datos de la lista de usuarios.
					(USUARIO ) us. estructuras de los usuarios.
   Objetivo:  insertar un nodo al inicio de los usuarios.
   Retorno: Ninguno.
*/


void insertnodoinuser(NODOUS **cab,NODOUS **cola, USUARIO us)
{
   NODOUS *nuevo;

   nuevo = (NODOUS *) malloc(sizeof(NODOUS));

   nuevo->user = us;

   nuevo->sig = *cab;

   *cab = nuevo;

   return;
}



/*
   Función: insertnodoinautor
   Argumentos: (NODOAUT **) cab. nodo con los datos de la lista de autor.
					(NODOAUT **) cola. nodo con los datos de la lista de autor.
					(AUTOR ) aut. estructuras de los autores.
   Objetivo:  insertar un nodo al inicio de los autores.
   Retorno: Ninguno.
*/


void insertnodoinautor(NODOAUT **cab, NODOAUT **cola, AUTOR aut)
{
   NODOAUT *nuevo;

   nuevo = (NODOAUT *) malloc(sizeof(NODOAUT));

   nuevo->aut = aut;

   nuevo->sig = *cab;

   *cab = nuevo;

   return;
}

/*
   Función: InsertarColaListauser
   Argumentos: (NODOUS **) cabeza. nodo con los datos de la lista de ususario.
					(NODOUS **) cola. nodo con los datos de la lista de usuarios.
					(USUARIO ) user. estructuras de los usuarios.
   Objetivo:  insertar un nodo al final de los usuarios.
   Retorno: Ninguno.
*/


void InsertarColaListauser(NODOUS **cabeza,NODOUS **cola, USUARIO user)
{
   NODOUS *pnuevo;
   pnuevo = (NODOUS *) malloc(sizeof(NODOUS));
   pnuevo->user = user;
   pnuevo->sig = NULL;
   pnuevo->ant = *cola;

   if ( *cabeza ==  NULL )
      *cabeza = pnuevo;
   if ( *cola )
      (*cola)->sig = pnuevo;
   *cola = pnuevo;
}


/*
   Función: InsertarColaListautor
   Argumentos: (NODOAUT **) cabeza. nodo con los datos de la lista de autor.
					(NODOAUT **) cola. nodo con los datos de la lista de autor.
					(AUTOR ) aut. estructuras de los autores.
   Objetivo:  insertar un nodo al inicio de los autores.
   Retorno: Ninguno.
*/

void InsertarColaListautor(NODOAUT **cabeza,NODOAUT **cola, AUTOR aut)
{
   NODOAUT *pnuevo;
   pnuevo = (NODOAUT *) malloc(sizeof(NODOAUT));
   pnuevo->aut = aut;
   pnuevo->sig = NULL;
   pnuevo->ant = *cola;

   if ( *cabeza ==  NULL )
      *cabeza = pnuevo;
   if ( *cola )
      (*cola)->sig = pnuevo;

   *cola = pnuevo;
}


/*
   Función: InsertarColaListautor
   Argumentos: (NODOLIB **) cab. nodo con los datos de la lista de libros.
					(NODOLIB **) cola. nodo con los datos de la lista de libros.
					(LIBRO ) li. estructuras de los libros.
   Objetivo:  insertar un nodo al inicio de los autores.
   Retorno: Ninguno.
*/

void insertnodoinlib(NODOLIB **cab,NODOLIB **cola, LIBRO li)
{
   NODOLIB *nuevo;

   nuevo = (NODOLIB *) malloc(sizeof(NODOLIB));

   nuevo->lib = li;

   nuevo->sig = *cab;

   *cab = nuevo;

   return;
}


/*
   Función: InsertarColaListlib
   Argumentos: (NODOLIB **) cab. nodo con los datos de la lista de libros.
					(NODOLIB **) cola. nodo con los datos de la lista de libros.
					(LIBRO ) li. estructuras de los libros.
   Objetivo:  insertar un nodo al final de los autores.
   Retorno: Ninguno.
*/

void InsertarColaListlib(NODOLIB **cabeza,NODOLIB **cola, LIBRO li)
{
   NODOLIB *pnuevo;
   pnuevo = (NODOLIB *) malloc(sizeof(NODOLIB));
   pnuevo->lib = li;
   pnuevo->sig = NULL;
   pnuevo->ant = *cola;

   if ( *cabeza ==  NULL )
      *cabeza = pnuevo;
   if ( *cola )
      (*cola)->sig = pnuevo;

   *cola = pnuevo;
}


/*
   Función: insertnodoinpres
   Argumentos: (NODOPRES **) cab. nodo con los datos de la lista de prestamos.
					(NODOPRES **) cola. nodo con los datos de la lista de prestamos.
					(PRESTAMOS ) pres. estructuras de los prestamos.
   Objetivo:  insertar un nodo al inicio de los prestamos.
   Retorno: Ninguno.
*/


void insertnodoinpres(NODOPRES **cab,NODOPRES **cola,PRESTAMOS pres){
	NODOPRES *nuevo;

   nuevo = (NODOPRES *) malloc(sizeof(NODOPRES));

   nuevo->pres = pres;

   nuevo->sig = *cab;

   *cab = nuevo;

}

/*
   Función: insertnodoinpres
   Argumentos: (NODOPRES **) cabeza. nodo con los datos de la lista de prestamos.
					(NODOPRES **) cola. nodo con los datos de la lista de prestamos.
					(PRESTAMOS ) pres. estructuras de los prestamos.
   Objetivo:  insertar un nodo al final de los prestamos.
   Retorno: Ninguno.
*/

void InsertarColaListpres(NODOPRES **cabeza,NODOPRES **cola, PRESTAMOS pres){

   NODOPRES *pnuevo;
   pnuevo = (NODOPRES *) malloc(sizeof(NODOPRES));
   pnuevo->pres = pres;
   pnuevo->sig = NULL;
   pnuevo->ant = *cola;

   if ( *cabeza ==  NULL )
      *cabeza = pnuevo;
   if ( *cola )
      (*cola)->sig = pnuevo;

   *cola = pnuevo;	

}


/*
   Función: caplib
   Argumentos:	(NODOAUT **) cab. nodo con los datos de la lista de autores.
					(NODOAUT **) cobe. nodo con los datos de la lista de autores.
					(int ) n. cantidad de campos a presentar.
   Objetivo:  mostrar los campos para modificar el libro.
   Retorno: li.
*/


LIBRO caplib(NODOAUT **cab,NODOAUT *cabe, int n){
	

   LIBRO li;
   NODOAUT *ind,*anterior;
	AUTOR *aut;


	int dia,mes,anio;
	char campofecha[LENLINE] = "  /  /    ";
	int cont=0,opc, sub=0;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};
	char edi[6] = {0};
	int nom =1, apel=1;
	if(n>0){

	system("cls");
	printf("ISBN: ");
	memset(li.ISBN , 0,LENISBN );
	capfieldAlphaNums(li.ISBN,LENISBN,8,1);

	printf("\nTitulo: ");
	memset(li.titulo , 0, LENTITULO);	
   capfieldAlpha(li.titulo,LENTITULO,8,2);

	printf("\nEditora: ");
	memset(li.editora, 0, LENEDIT);	
   capfieldAlpha(li.editora,LENEDIT,8,3);

	printf("\nFecha de edicion (dd/mm/yy): ");
	fechascat(campofecha, &dia, &mes, &anio, 10,35,4);
	li.fechaedicion.dd = dia;
	li.fechaedicion.mm = mes;
	li.fechaedicion.yyy = anio;
	
	printf("\n Edicion: ");
	capnums2(edi,5,8,5);
	li.edicion = atoi(edi);

   for (ind = cabe; ind; ind = ind->sig){
		if(nom<strlen(ind->aut.nombres)){
			nom = strlen(ind->aut.nombres);
		}			
	}
   for (ind = cabe; ind; ind = ind->sig){
		if(apel<strlen(ind->aut.apellidos)){
			apel = strlen(ind->aut.apellidos);
		}			
	}
	sprintf(titulolista," %-*s %-*s %-*s ",LENIDAUTOR,"ID",nom,"Nombres",apel,"Apellidos");
  for (ind = cabe; ind; ind = ind->sig){
		sprintf(lista[cont]," %-*s %-*s %-*s \n",LENIDAUTOR,ind->aut.idautor,nom,ind->aut.nombres,apel,
					ind->aut.apellidos);
		cont++;
	}
	opc = showmenu(lista,titulolista,cont,1,7,0);

   for ( ind = *cab; ind, sub<=opc; anterior = ind, ind = ind->sig)
   {
      if (sub==opc)
      {
			strcpy(li.idautor, ind->aut.idautor);
      }
		sub++;
   }	
	
	}else{
		printf("ERROR");
	}
   return li;
}


/*
   Función: cappres
   Argumentos:	(NODOUS **) cabus. nodo con los datos de la lista de usuarios.
					(NODOPRES **) pre. nodo con los datos de la lista de prestamos.
					(NODOUS **) cabs. nodo con los datos de la lista de ususarios.
					(NODOLIB **) coblib. nodo con los datos de la lista de libros.
					(NODOLIB **) cobl. nodo con los datos de la lista de libros.
					(int ) n. cantidad de campos a presentar.
   Objetivo:  capturar los prestamos.
   Retorno: pres.
*/

PRESTAMOS cappres(NODOUS **cabus,NODOPRES **pre,NODOUS *cabs,NODOLIB **cablib, NODOLIB *cabl,char *fec,int n){
	
	PRESTAMOS pres;
   NODOUS *inds,*anteriors;
	USUARIO *us;
   NODOLIB *indl,*anteriorl;
	LIBRO *li;
	NODOPRES *inpres,*antepres;
	system("cls");
	int cont=0,cont2=0,opc,opc2, sub=0,sub2=0;
	int siesta=0;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};
	int dia,mes,anio;
	char campofecha[LENLINE] = "  /  /    ";
	char campofecha2[LENLINE] = "  /  /    ";
	struct tm a = {0,0,0};
	struct tm b = {0,0,0};
	char camponums[9] = {0};
	int diferencia;
	int nom=5,apel=5;
   for (inds = cabs; inds; inds = inds->sig){
		if(nom<strlen(inds->user.nombres)){
			nom = strlen(inds->user.nombres);
		}			
	}
   for (inds = cabs; inds; inds = inds->sig){
		if(apel<strlen(inds->user.apellidos)){
			apel = strlen(inds->user.apellidos);
		}			
	}

	printf("Seleccionar Usuario:");
	sprintf(titulolista,"%-*s %-*s %-*s %-*s",LENIDDOC,"ID",nom,"Nombres",apel,"Apellidos",LENTELEFONO,"Telefono");
   for (inds = cabs; inds; inds = inds->sig){
		sprintf(lista[cont],"%-*s %-*s %-*s %-*s\n",LENIDDOC,inds->user.iddocumento,nom,inds->user.nombres,apel,
					inds->user.apellidos,LENTELEFONO,inds->user.telefono);
		cont++;
	}
	opc = showmenu(lista,titulolista,cont,1,2,0);
	system("cls");
	printf("Seleccionar Libro:");

	int isn=1, titulo = 1, editor =1, autor=1;

   for (indl = cabl; indl; indl = indl->sig){
		if(isn < strlen(indl->lib.ISBN))
			isn = strlen(indl->lib.ISBN);
	}
   for (indl = cabl; indl; indl = indl->sig){
		if(titulo < strlen(indl->lib.titulo))
			titulo = strlen(indl->lib.titulo);
	}
   for (indl = cabl; indl; indl = indl->sig){
		if(editor < strlen(indl->lib.editora))
			editor = strlen(indl->lib.editora);
	}

	sprintf(titulolista,"%-*s %-*s %-*s %-*s %-*s %s",isn,"ISBN",titulo,"Titulo",editor,"Editora",LENIDAUTOR,"IdAutor",10,"FechaEdi","Edicion ");
   for (indl = cabl; indl; indl = indl->sig){
		sprintf(lista[cont2],"%-*s %-*s %-*s %-*s %02d/%02d/%04d %5d   \n",isn,indl->lib.ISBN,titulo,indl->lib.titulo,editor,indl->lib.editora
					,LENIDAUTOR,indl->lib.idautor,indl->lib.fechaedicion.dd,indl->lib.fechaedicion.mm,indl->lib.fechaedicion.yyy, indl->lib.edicion);
		cont2++;
	}
	opc2=showmenu(lista,titulolista,cont2,1,2,0);

   //for (inds = cabs; inds; inds = inds->sig){
	//	if(strcmpi())
	//}

   for ( inds = *cabus; inds, sub<=opc; anteriors = inds, inds = inds->sig)
   {
      if (sub==opc){
			strcpy(pres.iddocumento, inds->user.iddocumento);
      }
		sub++;
   }

  for ( indl = *cablib; indl, sub2<=opc2; anteriorl = indl, indl = indl->sig)
   {
      if (sub2==opc2)
      {
			strcpy(pres.ISBN, indl->lib.ISBN);
      }
		sub2++;
   }

	system("cls");

	printf("Fecha actual (dd/mm/yy):");
	//fechascat(campofecha, &dia, &mes, &anio, 10,35,4);
   pres.fecprestamo.dd= atoi(strncpy(campofecha,fec,2));
   pres.fecprestamo.mm= atoi(strncpy(campofecha,fec+3,2));
   pres.fecprestamo.yyy= atoi(strncpy(campofecha,fec+6,4));
	strcpy(campofecha,fec);
	capf(campofecha,10,30,1);

	printf("\nFecha de devolucion: (dd/mm/yy): ");
	fechascat(campofecha2, &dia, &mes, &anio, 10,35,2);
	pres.fecdevolucion.dd= dia;
	pres.fecdevolucion.mm= mes;
	pres.fecdevolucion.yyy= anio;

	a.tm_mday = pres.fecprestamo.dd;
	a.tm_mon= 	pres.fecprestamo.mm-1;
	a.tm_year = pres.fecprestamo.yyy-1900;
	b.tm_mday = pres.fecdevolucion.dd;
	b.tm_mon = 	pres.fecdevolucion.mm-1;
	b.tm_year = pres.fecdevolucion.yyy-1900;
	time_t x = mktime(&a);
	time_t y = mktime(&b);

	diferencia = difftime(y, x) / (60 * 60 * 24);
	printf("\nCantidad de dias: %d",diferencia);
   printf("\nMonto: ");
	capnums2(camponums, 8,8,4);
	pres.montoprestamo= atof(camponums);

	sprintf(camponums,"%s","         ");

   printf("\nRecargo: ");
	capnums2(camponums, 8,8,5);
	pres.recargodiario= atof(camponums);
	return pres;
}

/*
   Función: cappres
   Argumentos:	(NODOUS **) cabus. nodo con los datos de la lista de usuarios.
					(NODOPRES **) pre. nodo con los datos de la lista de prestamos.
					(NODOUS **) cabs. nodo con los datos de la lista de ususarios.
					(NODOLIB **) coblib. nodo con los datos de la lista de libros.
					(NODOLIB **) cobl. nodo con los datos de la lista de libros.
					(char *)		fed. la fecha actual en cadena de caracteres.
					(int ) n. cantidad de campos a presentar.
   Objetivo:  capturar los prestamos.
   Retorno: Ninguno.
*/
PRESTAMOS modpres(NODOUS **cabus,NODOPRES **pre,NODOUS *cabs,NODOLIB **cablib, NODOLIB *cabl,char *fec,int n){
	
	PRESTAMOS pres;
   NODOUS *inds,*anteriors;
	USUARIO *us;
   NODOLIB *indl,*anteriorl;
	LIBRO *li;
	NODOPRES *ind,*antepres;
	int cont=0;
	int dia, mes, anio;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};	
	char pregunta[TAMAOPC][TAMAOPC] ={  "  no    ", "  si    "};
   char tipregunta[TAMAOPC] = "Guardar?";
	char fecha[11]={0}, fecha2[11] = {0};
	char monto[10]={0},recargo[10]={0};
	int isbn =1,opc=0,sub=0;
   char tecla;
	int quest;
	int opcsel =0;
	char strdate[11]={0},strdate2[11]={0};
	system("cls");
	for (ind = *pre; ind; ind = ind->sig){
		if(isbn<strlen(ind->pres.ISBN))
			isbn = strlen(ind->pres.ISBN);		
	}
	sprintf(titulolista,"%-*s %-*s %-*s %-*s %-*s",LENIDDOC,"Documento",isbn,"ISBN",10,"FecPrest",10,"FecDev",6,"Monto  ");
   for (ind = *pre; ind; ind = ind->sig){
		sprintf(lista[cont],"%-*s %-*s %02d/%02d/%04d %02d/%02d/%04d %5.2f \n",LENIDDOC,ind->pres.iddocumento,isbn,ind->pres.ISBN,
					ind->pres.fecprestamo.dd,ind->pres.fecprestamo.mm,ind->pres.fecprestamo.yyy,ind->pres.fecdevolucion.dd,ind->pres.fecdevolucion.mm,
					ind->pres.fecdevolucion.yyy, ind->pres.montoprestamo);
		cont++;
	}
	opc = showmenu(lista,titulolista,cont,1,2,0);
	system("cls");
   for ( ind = *pre; ind, sub<=opc; antepres = ind, ind = ind->sig)
   {
      if (sub==opc){
	
			printf("Documento del Usuario: ");
			capf(ind->pres.iddocumento,LENIDDOC-1,23,1);
			printf("\nISB del libro: ");
			capf(ind->pres.ISBN,strlen(ind->pres.ISBN),15,2);
			printf("\nFecha inicio: ");
			sprintf(fecha,"%02d/%02d/%04d",ind->pres.fecprestamo.dd,ind->pres.fecprestamo.mm,ind->pres.fecprestamo.yyy);
			capf(fecha,10,20,3);
			printf("\nFecha de devolucion: ");
			sprintf(fecha2,"%02d/%02d/%04d",ind->pres.fecdevolucion.dd, ind->pres.fecdevolucion.mm, ind->pres.fecdevolucion.yyy);
			capf(fecha2,10,20,4);
			printf("\nMonto: ");
			sprintf(monto,"%.2f",ind->pres.montoprestamo);
			capf(monto,6,8,5);
			printf("\nRecargo: ");
			sprintf(recargo,"%.2f",ind->pres.recargodiario);
			capf(recargo,6,8,6);
		   do{
				if(opcsel==0){
					seleccion(20+10+1,3);
					fechascat(fecha,&dia,&mes,&anio,10,20,3);
					eliminar(20+10+1,3,21,3);
				}else if(opcsel ==1){
					seleccion(20+10+1,4);
					fechascat(fecha2,&dia,&mes,&anio,10,20,4);	
					eliminar(20+10+1,4,21,4);
				}else if(opcsel==2){
					seleccion(8+6+1,5);
					capnums(monto,6,8,5);	
					eliminar(8+6+1,5,9,5);
				}else if(opcsel == 3){
					seleccion(8+6+1,6);
					capnums(recargo,6,8,6);	
					eliminar(8+6+1,6,9,6);
				}
		      do{
		         tecla = getch();
		      }while (tecla != ESC && tecla != ARRIBA && tecla != ABAJO);
		
		      if ( tecla != ESC )
		      {
		
		         if ( tecla == ARRIBA )
		         {
		            if ( opcsel != 0 )
		               opcsel --;
		            else
		               opcsel = n - 1;
		         }
		         if ( tecla == ABAJO )
		         {
		            if ( opcsel < n - 1 )
		               opcsel ++;
		            else
		               opcsel = 0;
		         }
		      }
		      else
		         opcsel = n - 1;
				
		   }while ( tecla != ESC );
			system("cls");
			quest = showmenu(pregunta,tipregunta,2,1,2,0);
			if(quest ==1){
				ind->pres.montoprestamo = atof(monto);
				ind->pres.recargodiario = atof(recargo);
				ind->pres.fecprestamo.dd = atoi(strncpy(strdate,fecha,2));
				ind->pres.fecprestamo.mm = atoi(strncpy(strdate,fecha+3,2));
				ind->pres.fecprestamo.yyy = atoi(strncpy(strdate,fecha+6,4));
				ind->pres.fecdevolucion.dd = atoi(strncpy(strdate2,fecha2,2));
				ind->pres.fecdevolucion.mm = atoi(strncpy(strdate2,fecha2+3,2));
				ind->pres.fecdevolucion.yyy = atoi(strncpy(strdate2,fecha2+6,4));
			}
		}
		sub++;
	}


}

/*
   Función: listarpres
   Argumentos: (NODOPRES *) cabeza. envia la cabeza de la lista.  
   Objetivo:  listar todos los prestamos realizados.
   Retorno: Ninguno.
*/


void listarpres(NODOPRES *cabeza){
	
	NODOPRES *ind;
	int cont=0;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};
	int isbn =1;
	for (ind = cabeza; ind; ind = ind->sig){
		if(isbn<strlen(ind->pres.ISBN))
			isbn = strlen(ind->pres.ISBN);		
	}
	sprintf(titulolista,"%-*s %-*s %-*s %-*s %-*s",LENIDDOC,"Documento",isbn,"ISBN",10,"FecPrest",10,"FecDev",6,"Monto  ");
   for (ind = cabeza; ind; ind = ind->sig){
		sprintf(lista[cont],"%-*s %-*s %02d/%02d/%04d %02d/%02d/%04d %5.2f \n",LENIDDOC,ind->pres.iddocumento,isbn,ind->pres.ISBN,
					ind->pres.fecprestamo.dd,ind->pres.fecprestamo.mm,ind->pres.fecprestamo.yyy,ind->pres.fecdevolucion.dd,ind->pres.fecdevolucion.mm,
					ind->pres.fecdevolucion.yyy, ind->pres.montoprestamo);
		cont++;
	}
	showmenu(lista,titulolista,cont,1,2,0);
}

void listarlib(NODOLIB *cabeza){
	NODOLIB *ind;

	int cont=0;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};
	int isn=1, titulo = 1, editor =1, autor=1;
   for (ind = cabeza; ind; ind = ind->sig){
		if(isn < strlen(ind->lib.ISBN))
			isn = strlen(ind->lib.ISBN);
	}
   for (ind = cabeza; ind; ind = ind->sig){
		if(titulo < strlen(ind->lib.titulo))
			titulo = strlen(ind->lib.titulo);
	}
   for (ind = cabeza; ind; ind = ind->sig){
		if(editor < strlen(ind->lib.editora))
			editor = strlen(ind->lib.editora);
	}

	sprintf(titulolista,"%-*s %-*s %-*s %-*s %-*s %s",isn,"ISBN",titulo,"Titulo",editor,"Editora",LENIDAUTOR,"IdAutor",10,"FechaEdi","Edicion ");
   for (ind = cabeza; ind; ind = ind->sig){
		sprintf(lista[cont],"%-*s %-*s %-*s %-*s %02d/%02d/%04d %5d   \n",isn,ind->lib.ISBN,titulo,ind->lib.titulo,editor,ind->lib.editora
					,LENIDAUTOR,ind->lib.idautor,ind->lib.fechaedicion.dd,ind->lib.fechaedicion.mm,ind->lib.fechaedicion.yyy, ind->lib.edicion);
		cont++;
	}
	showmenu(lista,titulolista,cont,1,2,0);

}

/*
   Función: salvarlib
   Argumentos: (NODOLIB *) cabeza. envia la cabeza de la lista.  
   Objetivo:  guardar en un archivo y poner el contenido en la lista de libros.
   Retorno: Ninguno.
*/

void salvarlib(NODOLIB *cabeza){
	NODOLIB *IND;
   FILE *pf;

   if ( (pf = fopen(ARCHLIBS,"wb")) == NULL )
   {
      printf("Error salvando archivo: %s\n",ARCHLIBS);
      exit(-1);
   }

   for (IND = cabeza; IND; IND = IND->sig)
   {
      fwrite(&IND->lib,sizeof(LIBRO),1,pf);
   }

   fclose(pf);
}

/*
   Función: salvaruser
   Argumentos: (NODOUS *) cabeza. envia la cabeza de la lista.  
   Objetivo:  guardar en un archivo y poner el contenido en la lista de ususarios.
   Retorno: Ninguno.
*/


void salvaruser(NODOUS *cabeza){
   NODOUS *IND;
   FILE *pf;

   if ( (pf = fopen(ARCHUS,"wb")) == NULL )
   {
      printf("Error salvando archivo: %s\n",ARCHUS);
      exit(-1);
   }

   for (IND = cabeza; IND; IND = IND->sig)
   {
      fwrite(&IND->user,sizeof(USUARIO),1,pf);
   }

   fclose(pf);
}

/*
   Función: salvaruser
   Argumentos: (NODOPRES *) cabeza. envia la cabeza de la lista.  
   Objetivo:  guardar en un archivo y poner el contenido en la lista de prestamos.
   Retorno: Ninguno.
*/

void salvarpres(NODOPRES *cabeza){
	
   NODOPRES *IND;
   FILE *pf;

   if ( (pf = fopen(ARCHPRES,"wb")) == NULL )
   {
      printf("Error salvando archivo: %s\n",ARCHPRES);
      exit(-1);
   }

   for (IND = cabeza; IND; IND = IND->sig)
   {
      fwrite(&IND->pres,sizeof(PRESTAMOS),1,pf);
   }

   fclose(pf);
}



/*
   Función: usererror
   Argumentos: Ninguno
   Objetivo:  imprimir un error cuando no se pueda eliminar usuario
   Retorno: Ninguno.
*/

void usererror(){
	printf("NO SE PUDO ELIMINAR EL USUARIO, PORQUE TIENE UN LIBRO PRESTADO");
	getch();
}



/*
   Función: Eliminar User 
   Argumentos: (NODOUS **) cab. manda la cabeza de la lista de los usuarios.
					(NODOPRES **)pre. manda la cabeza de la lsita de los prestamos.
					(NODOUS **)cabe.  manda la cabeza de la lsita de los usuarios.
   Objetivo:  Eliminar un usuario de la lista.
   Retorno: Ninguno.
*/


int EliminarUser(NODOUS **cab,NODOPRES **pre,NODOUS *cabe){
	NODOPRES *inpres,*antepres;
   NODOUS *ind,*anterior;
	USUARIO *us;
	system("cls");
	char pregunta[TAMAOPC][TAMAOPC] ={  "   no    ", "   si    "};
   char tipregunta[TAMAOPC] = "Eliminar?";
	int cont=0,opc, sub=0,siesta=0,quest =0;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};
	int nom=5,apel=5;
   for (ind = cabe; ind; ind = ind->sig){
		if(nom<strlen(ind->user.nombres)){
			nom = strlen(ind->user.nombres);
		}			
	}
   for (ind = cabe; ind; ind = ind->sig){
		if(apel<strlen(ind->user.apellidos)){
			apel = strlen(ind->user.apellidos);
		}			
	}
	sprintf(titulolista,"%-*s %-*s %-*s %-*s",LENIDDOC,"ID",nom,"Nombres",apel,"Apellidos",LENTELEFONO,"Telefono");
   for (ind = cabe; ind; ind = ind->sig){
		sprintf(lista[cont],"%-*s %-*s %-*s %-*s\n",LENIDDOC,ind->user.iddocumento,nom,ind->user.nombres,apel,
					ind->user.apellidos,LENTELEFONO,ind->user.telefono);
		cont++;
	}
	opc = showmenu(lista,titulolista,cont,1,2,0);
	

   for ( ind = *cab; ind, sub<=opc; anterior = ind, ind = ind->sig)
   {
      if (sub==opc)
      {
	

	   	for ( inpres = *pre; inpres; antepres = inpres, inpres = inpres->sig){
      		if (stricmp(inpres->pres.iddocumento , ind->user.iddocumento)== 0){		
					siesta++;
				}
			}		
			if(siesta==0){
				system("cls");
				quest = showmenu(pregunta,tipregunta,2,1,2,0);
				if(quest ==1){
		         if ( *cab == ind )
		            *cab = (*cab)->sig; // *cab = ind->sig
		         else
		            anterior->sig = ind->sig;
		         free(ind);
		         return 1;
				}
			}else{
				usererror();
				return 0;
			}
      }
		sub++;
   }

   return 0;
}


/*
   Función: autorerror
   Argumentos: Ninguno
   Objetivo:  Imprimir un error si no se puede eliminar un autor..
   Retorno: Ninguno.
*/

void autorerror(){
	printf("NO SE PUDO ELIMINAR EL AUTOR, PORQUE TIENE UN LIBRO ASIGNADO");
	getch();
}


/*
   Función: EliminarAutor
   Argumentos: (NODOAUT **) cab. manda la cabeza de la lista de los autores.
					(NODOLIB **)cabl. manda la cabeza de la lsita de los libros.
					(NODOAUT *)cabe.  manda la cabeza de la lsita de los autores.
   Objetivo:  Eliminar un autor de la lista.
   Retorno: Ninguno.
*/

int EliminarAutor(NODOAUT **cab,NODOLIB **cabl,NODOAUT *cabe){
	NODOLIB *indl, *anteriorl;
   NODOAUT *ind,*anterior;
	system("cls");
	char pregunta[TAMAOPC][TAMAOPC] ={  "   no    ", "   si    "};
   char tipregunta[TAMAOPC] = "Eliminar?";
	int cont=0,opc, sub=0,siesta=0,quest=0;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};
	int nom=5,apel=5;

   for (ind = cabe; ind; ind = ind->sig){
		if(nom<strlen(ind->aut.nombres)){
			nom = strlen(ind->aut.nombres);
		}			
	}
   for (ind = cabe; ind; ind = ind->sig){
		if(apel<strlen(ind->aut.apellidos)){
			apel = strlen(ind->aut.apellidos);
		}			
	}
	sprintf(titulolista," %-*s %-*s %-*s %-*s ",LENIDAUTOR,"ID",nom,"Nombres",apel,"Apellidos",10,"Fec.Nacim");
   for (ind = cabe; ind; ind = ind->sig){
		sprintf(lista[cont]," %-*s %-*s %-*s %02d/%02d/%04d \n",LENIDAUTOR,ind->aut.idautor,nom,ind->aut.nombres,apel,
					ind->aut.apellidos,ind->aut.fechanacimiento.dd,ind->aut.fechanacimiento.mm,ind->aut.fechanacimiento.yyy);
		cont++;
	}
	opc = showmenu(lista,titulolista,cont,1,2,0);

   for ( ind = *cab; ind, sub<=opc; anterior = ind, ind = ind->sig)
   {
      if (sub==opc)
      {
			for ( indl = *cabl; indl; anteriorl = indl, indl = indl->sig){
      		if (stricmp(indl->lib.idautor, ind->aut.idautor)== 0){		
					siesta++;
				}
			}		
			if(siesta==0){
			system("cls");
			quest = showmenu(pregunta,tipregunta,2,1,2,0);
				if(quest ==1){
		         if ( *cab == ind )
		            *cab = (*cab)->sig; // *cab = ind->sig
		         else
		            anterior->sig = ind->sig;
		         free(ind);
		         return 1;
				}
			}else{
				autorerror();
				return 0;
			}
      }
		sub++;
   }

   return 0;
}


/*
   Función: libroerror
   Argumentos: Ninguno
   Objetivo:  Imprimir un error si no se puede eliminar un libro.
   Retorno: Ninguno.
*/

void libroerror(){
	printf("NO SE PUDO ELIMINAR EL LIBRO, PORQUE ESTA PRESTADO");
	getch();
}


/*
   Función: EliminarLibro
   Argumentos: (NODOLIB **) cab. manda la cabeza de la lista de los libros.
					(NODOPRES **)cabl. manda la cabeza de la lsita de los prestamos.
					(NODOLIB *)cabe.  manda la cabeza de la lsita de los libros.
   Objetivo:  Eliminar un libro de la lista.
   Retorno: Ninguno.
*/

int EliminarLibro(NODOLIB **cab,NODOPRES **cabl,NODOLIB *cabe){
	NODOPRES *indpr, *anteriorpr;
   NODOLIB *ind,*anterior;
	system("cls");
	char pregunta[TAMAOPC][TAMAOPC] ={  "   no    ", "   si    "};
   char tipregunta[TAMAOPC] = "Eliminar?";
	int cont=0,opc, sub=0,siesta=0,quest=0;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};
	int isn=1, titulo = 1, editor =1, autor=1;
   for (ind = cabe; ind; ind = ind->sig){
		if(isn < strlen(ind->lib.ISBN))
			isn = strlen(ind->lib.ISBN);
	}
   for (ind = cabe; ind; ind = ind->sig){
		if(titulo < strlen(ind->lib.titulo))
			titulo = strlen(ind->lib.titulo);
	}
   for (ind = cabe; ind; ind = ind->sig){
		if(editor < strlen(ind->lib.editora))
			editor = strlen(ind->lib.editora);
	}

	sprintf(titulolista,"%-*s %-*s %-*s %-*s %-*s %s",isn,"ISBN",titulo,"Titulo",editor,"Editora",LENIDAUTOR,"IdAutor",10,"FechaEdi","Edicion ");
   for (ind = cabe; ind; ind = ind->sig){
		sprintf(lista[cont],"%-*s %-*s %-*s %-*s %02d/%02d/%04d %5d   \n",isn,ind->lib.ISBN,titulo,ind->lib.titulo,editor,ind->lib.editora
					,LENIDAUTOR,ind->lib.idautor,ind->lib.fechaedicion.dd,ind->lib.fechaedicion.mm,ind->lib.fechaedicion.yyy, ind->lib.edicion);
		cont++;
	}
	opc = showmenu(lista,titulolista,cont,1,2,0);

   for ( ind = *cab; ind, sub<=opc; anterior = ind, ind = ind->sig)
   {
      if (sub==opc)
      {
			for ( indpr = *cabl; indpr; anteriorpr = indpr, indpr = indpr->sig){
      		if (stricmp(indpr->pres.ISBN, ind->lib.ISBN)== 0){		
					siesta++;
				}
			}		
			if(siesta==0){
			system("cls");
			quest = showmenu(pregunta,tipregunta,2,1,2,0);
				if(quest ==1){
		         if ( *cab == ind )
		            *cab = (*cab)->sig; // *cab = ind->sig
		         else
		            anterior->sig = ind->sig;
		         free(ind);
		         return 1;
				}
			}else{
				libroerror();
				return 0;
			}
      }
		sub++;
   }

   return 0;
}



/*
   Función: devolverpres
   Argumentos: (NODOPRES **)cab. manda la cabeza de la lsita de los prestamos.
					(NODOPRES *)cabe.  manda la cabeza de la lsita de los prestamos.
   Objetivo:  devolver los prestamos.
   Retorno: Ninguno.
*/

void devolverpres(NODOPRES **cab,NODOPRES *cabe){
	
   NODOPRES *ind,*anterior;
	system("cls");
	struct tm a = {0,0,0};
	struct tm b = {0,0,0};
	struct tm c ={0,0,0};
	int dia,mes,anio;
	char campon[9]={0};
	float nums=0;
	char campofecha[LENLINE] = "  /  /    ";
   int diferencia=0,diferencia2=0;
	char pregunta[TAMAOPC][TAMAOPC] ={  "   no    ", "   si    "};
   char tipregunta[TAMAOPC] = "Eliminar?";
	int cont=0,opc, sub=0,siesta=0,quest=0;
	float elnum;
   char lista[TAMAOPC][TAMAOPC] ={0};
   char titulolista[TAMAOPC]={0};
	sprintf(titulolista,"%-*s %-*s %-*s %-*s %-*s",LENIDDOC,"Documento",LENISBN,"ISBN",10,"FecPrest",10,"FecDev",6,"Monto  ");
   for (ind = cabe; ind; ind = ind->sig){
		sprintf(lista[cont],"%-*s %-*s %02d/%02d/%04d %02d/%02d/%04d %5.2f \n",LENIDDOC,ind->pres.iddocumento,LENISBN,ind->pres.ISBN,
					ind->pres.fecprestamo.dd,ind->pres.fecprestamo.mm,ind->pres.fecprestamo.yyy,ind->pres.fecdevolucion.dd,ind->pres.fecdevolucion.mm,
					ind->pres.fecdevolucion.yyy, ind->pres.montoprestamo);
		cont++;
	}
	opc = showmenu(lista,titulolista,cont,1,2,0);

   for ( ind = *cab; ind, sub<=opc; anterior = ind, ind = ind->sig)
   {
      if (sub==opc){
			system("cls");
			printf("\nFecha de devolucion(dd/mm/yy): ");
			fechascat(campofecha, &dia, &mes, &anio, 10,35,2);
			a.tm_mday = ind->pres.fecprestamo.dd;
			a.tm_mon= ind->pres.fecprestamo.mm-1;
			a.tm_year = ind->pres.fecprestamo.yyy-1900;
			b.tm_mday = ind->pres.fecdevolucion.dd;
			b.tm_mon = ind->pres.fecdevolucion.mm-1;
			b.tm_year = ind->pres.fecdevolucion.yyy-1900;
			c.tm_mday= dia;
			c.tm_mon= mes-1;
			c.tm_year = anio-1900;
			time_t x = mktime(&a);
			time_t y = mktime(&b);
			time_t z = mktime(&c);
			diferencia = difftime(y, x) / (60 * 60 * 24);
			diferencia2 = difftime(z,x)/ (60 * 60 * 24);

			if(diferencia - diferencia2>0){
	
			printf("\nUsted debe:%.2f",ind->pres.montoprestamo);
			   printf("\nMonto: ");
				do{
				capnums2(campon, 8,10,4);
				nums = atof(campon);
				}while(nums != ind->pres.montoprestamo);

			}else{
				elnum = ind->pres.montoprestamo + ( -1 *(diferencia - diferencia2))* ind->pres.recargodiario;
				printf("\nUsted debe: %.2f",elnum);
			   printf("\nMonto: ");
				do{
				capnums2(campon, 8,10,4);
				nums = atof(campon);
				}while(nums != elnum);
			}

	      if ( *cab == ind )
            *cab = (*cab)->sig; // *cab = ind->sig
         else
            anterior->sig = ind->sig;
         free(ind);
         break;
      }
		sub++;
   }

}


/*
   Función: salvarautor
   Argumentos: (NODOAUT **)cab. manda la cabeza de la lsita de los autores.
   Objetivo:  permite guardar en un archivo los autores.
   Retorno: Ninguno.
*/


void salvarautor(NODOAUT *cabeza){
   NODOAUT *IND;
   FILE *pf;

   if ( (pf = fopen(ARCHAUT,"wb")) == NULL )
   {
      printf("Error salvando archivo: %s\n",ARCHAUT);
      exit(-1);
   }

   for (IND = cabeza; IND; IND = IND->sig)
   {
      fwrite(&IND->aut,sizeof(AUTOR),1,pf);
   }

   fclose(pf);	

}


/*
   Función: freeus
   Argumentos: (NODOUS **)cab. manda la cabeza de la lsita de los ususarios.
   Objetivo:  libera la memoria de los nodos.
   Retorno: Ninguno.
*/

void freeus(NODOUS *cab)
{
   NODOUS *IND, *temp;
   for (IND = cab ; IND; temp = IND,IND = IND->sig,free(temp));

}


/*
   Función: freeus
   Argumentos: (NODOAUT **)cab. manda la cabeza de la lsita de los autores.
   Objetivo:  libera la memoria de los nodos.
   Retorno: Ninguno.
*/

void freeaut(NODOAUT *cab)
{
   NODOAUT *IND, *temp;
   for (IND = cab ; IND; temp = IND,IND = IND->sig,free(temp));

}


/*
   Función: freeus
   Argumentos: (NODOLIB **)cab. manda la cabeza de la lsita de los libros.
   Objetivo:  libera la memoria de los nodos.
   Retorno: Ninguno.
*/

void freelib(NODOLIB *cab)
{
   NODOLIB *IND, *temp;
   for (IND = cab ; IND; temp = IND,IND = IND->sig,free(temp));

}


/*
   Función: freeus
   Argumentos: (NODOPRES **)cab. manda la cabeza de la lsita de los prestamos.
   Objetivo:  libera la memoria de los nodos.
   Retorno: Ninguno.
*/

void freepres(NODOPRES *cab)
{
   NODOPRES *IND, *temp;
   for (IND = cab ; IND; temp = IND,IND = IND->sig,free(temp));

}
