import tkinter as tk
from tkinter import messagebox, simpledialog
from tkinter import ttk


class InventarioPapeleria:
    _instancia = None

    def _new_(cls):
        if cls._instancia is None:
            cls.instancia = super().new_(cls)
            cls._instancia.productos = {
                "lapiz": 50,
                "cuaderno": 30,
                "borrador": 25
            }
            print("Inventario creado (Singleton).")
        return cls._instancia

    def agregar_producto(self, nombre, cantidad):
        clave = nombre.lower().strip()
        self.productos[clave] = self.productos.get(clave, 0) + cantidad

    def vender(self, nombre, cantidad):
        clave = nombre.lower().strip()
        if clave not in self.productos:
            return False, f" El producto '{nombre}' no existe."
        if cantidad > self.productos[clave]:
            return False, f" Stock insuficiente. Solo hay {self.productos[clave]}."
        self.productos[clave] -= cantidad
        return True, f" Venta realizada: {cantidad} unidades de {nombre}"

    def obtener_inventario(self):
        return self.productos


class Empleado:
    def _init_(self, nombre):
        self.nombre = nombre
        self.inventario = InventarioPapeleria()

    def vender_producto(self, producto, cantidad):
        return self.inventario.vender(producto, cantidad)

    def obtener_inventario(self):
        return self.inventario.obtener_inventario()


class VentanaPrincipal(tk.Toplevel):
    _instancia = None

    def _new_(cls, empleado):
        if cls._instancia is None or not tk.Toplevel.winfo_exists(cls._instancia):
            cls.instancia = super().new_(cls)
            cls._instancia._inicializada = False
            print("Ventana principal creada (Singleton).")
        else:
            cls._instancia.deiconify()
            cls._instancia.lift()
            return cls._instancia
        return cls._instancia

    def _init_(self, empleado):
        if self._inicializada:
            return
        super()._init_()
        self._inicializada = True

        self.title(f"Sistema de Papelería - {empleado.nombre}")
        self.geometry("420x320")
        self.empleado = empleado

        tk.Label(self, text=f"👤 Bienvenido, {empleado.nombre}",
                 font=("Arial", 14, "bold")).pack(pady=10)

        ttk.Button(self, text=" Mostrar Inventario", command=self.mostrar_inventario).pack(pady=10)
        ttk.Button(self, text=" Vender Producto", command=self.vender_producto).pack(pady=10)
        ttk.Button(self, text=" Cerrar Sesión", command=self.cerrar_sesion).pack(pady=20)

    # Métodos
    def mostrar_inventario(self):
        VentanaInventario(self.empleado.obtener_inventario())

    def vender_producto(self):
        producto = simpledialog.askstring("Venta", "Nombre del producto:")
        if not producto:
            return
        try:
            cantidad = int(simpledialog.askstring("Venta", "Cantidad a vender:"))
            ok, msg = self.empleado.vender_producto(producto, cantidad)
            messagebox.showinfo("Resultado", msg)
        except Exception:
            messagebox.showerror("Error", "Debe ingresar una cantidad válida.")

    def cerrar_sesion(self):
        self.destroy()
        VentanaPrincipal._instancia = None


class VentanaInventario(tk.Toplevel):
    _instancia = None

    def _new_(cls, inventario):
        #  Si ya existe y sigue viva, simplemente la trae al frente
        if cls._instancia is not None and tk.Toplevel.winfo_exists(cls._instancia):
            cls._instancia.lift()
            cls._instancia.focus()
            return cls._instancia

        # Si no existe, crea una nueva
        cls.instancia = super().new_(cls)
        cls._instancia._inicializada = False
        print("Ventana de inventario creada (Singleton).")
        return cls._instancia

    def _init_(self, inventario):
        if self._inicializada:
            return
        super()._init_()
        self._inicializada = True

        self.title(" Inventario actual")
        self.geometry("300x250")
        self.inventario = inventario

        frame = ttk.Frame(self)
        frame.pack(pady=10, padx=10, fill="both", expand=True)

        self.tree = ttk.Treeview(frame, columns=("Producto", "Stock"), show="headings", height=6)
        self.tree.heading("Producto", text="Producto")
        self.tree.heading("Stock", text="Stock")
        self.tree.column("Producto", width=120)
        self.tree.column("Stock", width=80)
        self.tree.pack(fill="both", expand=True)

        self.actualizar_tabla()

        ttk.Button(self, text="Cerrar", command=self.cerrar).pack(pady=10)

    def actualizar_tabla(self):
        for item in self.tree.get_children():
            self.tree.delete(item)
        for producto, stock in self.inventario.items():
            self.tree.insert("", "end", values=(producto.capitalize(), stock))

    def cerrar(self):
        self.destroy()
        VentanaInventario._instancia = None


class VentanaLogin(tk.Tk):
    def _init_(self):
        super()._init_()
        self.title("Login - Papelería Singleton")
        self.geometry("350x200")

        tk.Label(self, text=" Bienvenido al sistema", font=("Arial", 13, "bold")).pack(pady=10)
        tk.Label(self, text="Ingrese su nombre de empleado:").pack()
        self.entry_nombre = tk.Entry(self)
        self.entry_nombre.pack(pady=5)

        ttk.Button(self, text="Ingresar", command=self.ingresar).pack(pady=10)

    def ingresar(self):
        nombre = self.entry_nombre.get().strip()
        if not nombre:
            messagebox.showwarning("Advertencia", "Debe ingresar un nombre.")
            return
        empleado = Empleado(nombre)
        self.withdraw()  
        VentanaPrincipal(empleado)


if _name_ == "_main_":
    app = VentanaLogin()
    app.mainloop()