-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-06-2015 a las 13:51:22
-- Versión del servidor: 5.6.24
-- Versión de PHP: 5.5.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `facturacion2015`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE IF NOT EXISTS `clientes` (
  `id` int(10) unsigned NOT NULL,
  `nombre` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `nif` varchar(9) COLLATE utf8_spanish_ci NOT NULL,
  `dirCorreo` mediumtext COLLATE utf8_spanish_ci NOT NULL,
  `dirFactura` mediumtext COLLATE utf8_spanish_ci NOT NULL,
  `dirEnvio` mediumtext COLLATE utf8_spanish_ci NOT NULL,
  `contacto` mediumtext COLLATE utf8_spanish_ci NOT NULL,
  `porcRecargoEquivalencia` double NOT NULL,
  `porcDescuento` double NOT NULL,
  `fechaAlta` date NOT NULL,
  `baja` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `nombre`, `apellidos`, `nif`, `dirCorreo`, `dirFactura`, `dirEnvio`, `contacto`, `porcRecargoEquivalencia`, `porcDescuento`, `fechaAlta`, `baja`) VALUES
(1, 'Ignacio', 'Mingo Pascual', '20434900K', 'Cardenal Vicente Enrique', 'Calle Palazuelos', 'Calle Palazuelos', '655155061', 5.2, 30, '2015-06-08', 0),
(2, 'Javier', 'García', '2010212A', 'Avenida de los Naranjos', 'Avenida de los Naranjos', 'Avenida de los Naranjos', 'javier@gmail.com', 5, 10, '2015-06-10', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturas`
--

CREATE TABLE IF NOT EXISTS `facturas` (
  `id` int(10) unsigned NOT NULL,
  `clienteId` int(10) unsigned NOT NULL,
  `nombreCliente` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `numero` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `porcDescuento` double NOT NULL,
  `porcRecargoEquivalencia` double NOT NULL,
  `impTotal` double NOT NULL,
  `impRecargo` double NOT NULL,
  `impIva` double NOT NULL,
  `dirCorreo` mediumtext COLLATE utf8_spanish_ci NOT NULL,
  `dirFactura` mediumtext COLLATE utf8_spanish_ci NOT NULL,
  `dirEnvio` mediumtext COLLATE utf8_spanish_ci NOT NULL,
  `cobrada` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `facturas`
--

INSERT INTO `facturas` (`id`, `clienteId`, `nombreCliente`, `numero`, `fecha`, `porcDescuento`, `porcRecargoEquivalencia`, `impTotal`, `impRecargo`, `impIva`, `dirCorreo`, `dirFactura`, `dirEnvio`, `cobrada`) VALUES
(1, 1, '', 1, '2015-06-08', 10, 5.2, 500, 500, 500, 'Cardenal Vicente Enrique', 'Calle Palazuelos', 'Calle Palazuelos', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturasdetalle`
--

CREATE TABLE IF NOT EXISTS `facturasdetalle` (
  `id` int(10) unsigned NOT NULL,
  `facturaId` int(10) unsigned NOT NULL,
  `prodId` int(10) unsigned NOT NULL,
  `prodNombre` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `prodPrecio` double NOT NULL,
  `prodIva` double NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `facturasdetalle`
--

INSERT INTO `facturasdetalle` (`id`, `facturaId`, `prodId`, `prodNombre`, `prodPrecio`, `prodIva`, `cantidad`) VALUES
(1, 1, 1, 'iPhone', 500, 10.99, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE IF NOT EXISTS `productos` (
  `id` int(11) unsigned NOT NULL,
  `nombre` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `precio` double NOT NULL,
  `iva` double NOT NULL,
  `stock` int(11) NOT NULL,
  `descripcion` mediumtext COLLATE utf8_spanish_ci NOT NULL,
  `baja` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `nombre`, `precio`, `iva`, `stock`, `descripcion`, `baja`) VALUES
(1, 'iPhone 6 Plus', 699.99, 120, 20, 'Smartphone Apple', 0),
(2, 'Moto G', 189.89, 20.9, 5, 'Modelo de SmartPhone de Motorola', 0),
(3, 'Samsung Plus', 400, 10, 5, 'Tableta con Android Lollipop', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `administrador` tinyint(1) NOT NULL,
  `email` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `password`, `administrador`, `email`, `estado`) VALUES
(1, 'Ignacio Mingo', '1f953288e3383968f741e2d3618d8398c2a73ef33a49632a5014d6744dc9b30f', 1, 'igmingo@gmail.com', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `facturas`
--
ALTER TABLE `facturas`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `numero` (`numero`), ADD KEY `clienteId` (`clienteId`);

--
-- Indices de la tabla `facturasdetalle`
--
ALTER TABLE `facturasdetalle`
  ADD PRIMARY KEY (`id`), ADD KEY `facturaId` (`facturaId`), ADD KEY `prodId` (`prodId`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `facturas`
--
ALTER TABLE `facturas`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `facturasdetalle`
--
ALTER TABLE `facturasdetalle`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
