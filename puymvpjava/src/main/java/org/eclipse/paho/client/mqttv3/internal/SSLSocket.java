package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.net.*;

import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;


public abstract class SSLSocket extends Socket {
    /**
     * Used only by subclasses.
     * Constructs an uninitialized, unconnected TCP socket.
     */
    protected SSLSocket() {
        super();
    }


    /**
     * Used only by subclasses.
     * Constructs a TCP connection to a named host at a specified port.
     * This acts as the SSL client.
     * <p>
     * If there is a security manager, its <code>checkConnect</code>
     * method is called with the host address and <code>port</code>
     * as its arguments. This could result in a SecurityException.
     *
     * @param host name of the host with which to connect, or
     *             <code>null</code> for the loopback address.
     * @param port number of the server's port
     * @throws IOException              if an I/O error occurs when creating the socket
     * @throws SecurityException        if a security manager exists and its
     *                                  <code>checkConnect</code> method doesn't allow the operation.
     * @throws UnknownHostException     if the host is not known
     * @throws IllegalArgumentException if the port parameter is outside the
     *                                  specified range of valid port values, which is between 0 and
     *                                  65535, inclusive.
     * @see SecurityManager#checkConnect
     */
    protected SSLSocket(String host, int port)
            throws IOException, UnknownHostException {
        super(host, port);
    }


    /**
     * Used only by subclasses.
     * Constructs a TCP connection to a server at a specified address
     * and port.  This acts as the SSL client.
     * <p>
     * If there is a security manager, its <code>checkConnect</code>
     * method is called with the host address and <code>port</code>
     * as its arguments. This could result in a SecurityException.
     *
     * @param address the server's host
     * @param port    its port
     * @throws IOException              if an I/O error occurs when creating the socket
     * @throws SecurityException        if a security manager exists and its
     *                                  <code>checkConnect</code> method doesn't allow the operation.
     * @throws IllegalArgumentException if the port parameter is outside the
     *                                  specified range of valid port values, which is between 0 and
     *                                  65535, inclusive.
     * @throws NullPointerException     if <code>address</code> is null.
     * @see SecurityManager#checkConnect
     */
    protected SSLSocket(InetAddress address, int port)
            throws IOException {
        super(address, port);
    }


    /**
     * Used only by subclasses.
     * Constructs an SSL connection to a named host at a specified port,
     * binding the client side of the connection a given address and port.
     * This acts as the SSL client.
     * <p>
     * If there is a security manager, its <code>checkConnect</code>
     * method is called with the host address and <code>port</code>
     * as its arguments. This could result in a SecurityException.
     *
     * @param host          name of the host with which to connect, or
     *                      <code>null</code> for the loopback address.
     * @param port          number of the server's port
     * @param clientAddress the client's address the socket is bound to, or
     *                      <code>null</code> for the <code>anyLocal</code> address.
     * @param clientPort    the client's port the socket is bound to, or
     *                      <code>zero</code> for a system selected free port.
     * @throws IOException              if an I/O error occurs when creating the socket
     * @throws SecurityException        if a security manager exists and its
     *                                  <code>checkConnect</code> method doesn't allow the operation.
     * @throws UnknownHostException     if the host is not known
     * @throws IllegalArgumentException if the port parameter or clientPort
     *                                  parameter is outside the specified range of valid port values,
     *                                  which is between 0 and 65535, inclusive.
     * @see SecurityManager#checkConnect
     */
    protected SSLSocket(String host, int port,
                        InetAddress clientAddress, int clientPort)
            throws IOException, UnknownHostException {
        super(host, port, clientAddress, clientPort);
    }


    /**
     * Used only by subclasses.
     * Constructs an SSL connection to a server at a specified address
     * and TCP port, binding the client side of the connection a given
     * address and port.  This acts as the SSL client.
     * <p>
     * If there is a security manager, its <code>checkConnect</code>
     * method is called with the host address and <code>port</code>
     * as its arguments. This could result in a SecurityException.
     *
     * @param address       the server's host
     * @param port          its port
     * @param clientAddress the client's address the socket is bound to, or
     *                      <code>null</code> for the <code>anyLocal</code> address.
     * @param clientPort    the client's port the socket is bound to, or
     *                      <code>zero</code> for a system selected free port.
     * @throws IOException              if an I/O error occurs when creating the socket
     * @throws SecurityException        if a security manager exists and its
     *                                  <code>checkConnect</code> method doesn't allow the operation.
     * @throws IllegalArgumentException if the port parameter or clientPort
     *                                  parameter is outside the specified range of valid port values,
     *                                  which is between 0 and 65535, inclusive.
     * @throws NullPointerException     if <code>address</code> is null.
     * @see SecurityManager#checkConnect
     */
    protected SSLSocket(InetAddress address, int port,
                        InetAddress clientAddress, int clientPort)
            throws IOException {
        super(address, port, clientAddress, clientPort);
    }


    /**
     * Returns the names of the cipher suites which could be enabled for use
     * on this connection.  Normally, only a subset of these will actually
     * be enabled by default, since this list may include cipher suites which
     * do not meet quality of service requirements for those defaults.  Such
     * cipher suites might be useful in specialized applications.
     *
     * @return an array of cipher suite names
     * @see #getEnabledCipherSuites()
     */
    public abstract String[] getSupportedCipherSuites();


    /**
     * Returns the names of the SSL cipher suites which are currently
     * enabled for use on this connection.  When an SSLSocket is first
     * created, all enabled cipher suites support a minimum quality of
     * service.  Thus, in some environments this value might be empty.
     * <p>
     * Even if a suite has been enabled, it might never be used.  (For
     * example, the peer does not support it, the requisite certificates
     * (and private keys) for the suite are not available, or an
     * anonymous suite is enabled but authentication is required.
     *
     * @return an array of cipher suite names
     * @see #getSupportedCipherSuites()
     */
    public abstract String[] getEnabledCipherSuites();


    /**
     * Sets the cipher suites enabled for use on this connection.
     * <p>
     * Each cipher suite in the <code>suites</code> parameter must have
     * been listed by getSupportedCipherSuites(), or the method will
     * fail.  Following a successful call to this method, only suites
     * listed in the <code>suites</code> parameter are enabled for use.
     * <p>
     * See {@link #getEnabledCipherSuites()} for more information
     * on why a specific ciphersuite may never be used on a connection.
     *
     * @param suites Names of all the cipher suites to enable
     * @throws IllegalArgumentException when one or more of the ciphers
     *                                  named by the parameter is not supported, or when the
     *                                  parameter is null.
     * @see #getSupportedCipherSuites()
     * @see #getEnabledCipherSuites()
     */
    public abstract void setEnabledCipherSuites(String suites[]);


    /**
     * Returns the names of the protocols which could be enabled for use
     * on an SSL connection.
     *
     * @return an array of protocols supported
     */
    public abstract String[] getSupportedProtocols();


    /**
     * Returns the names of the protocol versions which are currently
     * enabled for use on this connection.
     *
     * @return an array of protocols
     */
    public abstract String[] getEnabledProtocols();


    // Android-added: Added paragraph about contiguous protocols.

    /**
     * Sets the protocol versions enabled for use on this connection.
     * <p>
     * The protocols must have been listed by
     * <code>getSupportedProtocols()</code> as being supported.
     * Following a successful call to this method, only protocols listed
     * in the <code>protocols</code> parameter are enabled for use.
     * <p>
     * Because of the way the protocol version is negotiated, connections
     * will only be able to use a member of the lowest set of contiguous
     * enabled protocol versions.  For example, enabling TLSv1.2 and TLSv1
     * will result in connections only being able to use TLSv1.
     *
     * @param protocols Names of all the protocols to enable.
     * @throws IllegalArgumentException when one or more of
     *                                  the protocols named by the parameter is not supported or
     *                                  when the protocols parameter is null.
     * @see #getEnabledProtocols()
     */
    public abstract void setEnabledProtocols(String protocols[]);


    /**
     * Returns the SSL Session in use by this connection.  These can
     * be long lived, and frequently correspond to an entire login session
     * for some user.  The session specifies a particular cipher suite
     * which is being actively used by all connections in that session,
     * as well as the identities of the session's client and server.
     * <p>
     * This method will initiate the initial handshake if
     * necessary and then block until the handshake has been
     * established.
     * <p>
     * If an error occurs during the initial handshake, this method
     * returns an invalid session object which reports an invalid
     * cipher suite of "SSL_NULL_WITH_NULL_NULL".
     *
     * @return the <code>SSLSession</code>
     */
    public abstract SSLSession getSession();


    /**
     * Returns the {@code SSLSession} being constructed during a SSL/TLS
     * handshake.
     * <p>
     * TLS protocols may negotiate parameters that are needed when using
     * an instance of this class, but before the {@code SSLSession} has
     * been completely initialized and made available via {@code getSession}.
     * For example, the list of valid signature algorithms may restrict
     * the type of certificates that can used during TrustManager
     * decisions, or the maximum TLS fragment packet sizes can be
     * resized to better support the network environment.
     * <p>
     * This method provides early access to the {@code SSLSession} being
     * constructed.  Depending on how far the handshake has progressed,
     * some data may not yet be available for use.  For example, if a
     * remote server will be sending a Certificate chain, but that chain
     * has yet not been processed, the {@code getPeerCertificates}
     * method of {@code SSLSession} will throw a
     * SSLPeerUnverifiedException.  Once that chain has been processed,
     * {@code getPeerCertificates} will return the proper value.
     * <p>
     * Unlike {@link #getSession()}, this method does not initiate the
     * initial handshake and does not block until handshaking is
     * complete.
     *
     * @return null if this instance is not currently handshaking, or
     * if the current handshake has not progressed far enough to
     * create a basic SSLSession.  Otherwise, this method returns the
     * {@code SSLSession} currently being negotiated.
     * @throws UnsupportedOperationException if the underlying provider
     *                                       does not implement the operation.
     * @since 1.7
     */
    public SSLSession getHandshakeSession() {
        throw new UnsupportedOperationException();
    }


    /**
     * Registers an event listener to receive notifications that an
     * SSL handshake has completed on this connection.
     *
     * @param listener the HandShake Completed event listener
     * @throws IllegalArgumentException if the argument is null.
     * @see #startHandshake()
     * @see #removeHandshakeCompletedListener(HandshakeCompletedListener)
     */
    public abstract void addHandshakeCompletedListener(
            HandshakeCompletedListener listener);


    /**
     * Removes a previously registered handshake completion listener.
     *
     * @param listener the HandShake Completed event listener
     * @throws IllegalArgumentException if the listener is not registered,
     *                                  or the argument is null.
     * @see #addHandshakeCompletedListener(HandshakeCompletedListener)
     */
    public abstract void removeHandshakeCompletedListener(
            HandshakeCompletedListener listener);


    /**
     * Starts an SSL handshake on this connection.  Common reasons include
     * a need to use new encryption keys, to change cipher suites, or to
     * initiate a new session.  To force complete reauthentication, the
     * current session could be invalidated before starting this handshake.
     *
     * <P> If data has already been sent on the connection, it continues
     * to flow during this handshake.  When the handshake completes, this
     * will be signaled with an event.
     * <p>
     * This method is synchronous for the initial handshake on a connection
     * and returns when the negotiated handshake is complete. Some
     * protocols may not support multiple handshakes on an existing socket
     * and may throw an IOException.
     *
     * @throws IOException on a network level error
     * @see #addHandshakeCompletedListener(HandshakeCompletedListener)
     */
    public abstract void startHandshake() throws IOException;


    /**
     * Configures the socket to use client (or server) mode when
     * handshaking.
     * <p>
     * This method must be called before any handshaking occurs.
     * Once handshaking has begun, the mode can not be reset for the
     * life of this socket.
     * <p>
     * Servers normally authenticate themselves, and clients
     * are not required to do so.
     *
     * @param mode true if the socket should start its handshaking
     *             in "client" mode
     * @throws IllegalArgumentException if a mode change is attempted
     *                                  after the initial handshake has begun.
     * @see #getUseClientMode()
     */
    public abstract void setUseClientMode(boolean mode);


    /**
     * Returns true if the socket is set to use client mode when
     * handshaking.
     *
     * @return true if the socket should do handshaking
     * in "client" mode
     * @see #setUseClientMode(boolean)
     */
    public abstract boolean getUseClientMode();


    /**
     * Configures the socket to <i>require</i> client authentication.  This
     * option is only useful for sockets in the server mode.
     * <p>
     * A socket's client authentication setting is one of the following:
     * <ul>
     * <li> client authentication required
     * <li> client authentication requested
     * <li> no client authentication desired
     * </ul>
     * <p>
     * Unlike {@link #setWantClientAuth(boolean)}, if this option is set and
     * the client chooses not to provide authentication information
     * about itself, <i>the negotiations will stop and the connection
     * will be dropped</i>.
     * <p>
     * Calling this method overrides any previous setting made by
     * this method or {@link #setWantClientAuth(boolean)}.
     *
     * @param need set to true if client authentication is required,
     *             or false if no client authentication is desired.
     * @see #getNeedClientAuth()
     * @see #setWantClientAuth(boolean)
     * @see #getWantClientAuth()
     * @see #setUseClientMode(boolean)
     */
    public abstract void setNeedClientAuth(boolean need);


    /**
     * Returns true if the socket will <i>require</i> client authentication.
     * This option is only useful to sockets in the server mode.
     *
     * @return true if client authentication is required,
     * or false if no client authentication is desired.
     * @see #setNeedClientAuth(boolean)
     * @see #setWantClientAuth(boolean)
     * @see #getWantClientAuth()
     * @see #setUseClientMode(boolean)
     */
    public abstract boolean getNeedClientAuth();


    /**
     * Configures the socket to <i>request</i> client authentication.
     * This option is only useful for sockets in the server mode.
     * <p>
     * A socket's client authentication setting is one of the following:
     * <ul>
     * <li> client authentication required
     * <li> client authentication requested
     * <li> no client authentication desired
     * </ul>
     * <p>
     * Unlike {@link #setNeedClientAuth(boolean)}, if this option is set and
     * the client chooses not to provide authentication information
     * about itself, <i>the negotiations will continue</i>.
     * <p>
     * Calling this method overrides any previous setting made by
     * this method or {@link #setNeedClientAuth(boolean)}.
     *
     * @param want set to true if client authentication is requested,
     *             or false if no client authentication is desired.
     * @see #getWantClientAuth()
     * @see #setNeedClientAuth(boolean)
     * @see #getNeedClientAuth()
     * @see #setUseClientMode(boolean)
     */
    public abstract void setWantClientAuth(boolean want);


    /**
     * Returns true if the socket will <i>request</i> client authentication.
     * This option is only useful for sockets in the server mode.
     *
     * @return true if client authentication is requested,
     * or false if no client authentication is desired.
     * @see #setNeedClientAuth(boolean)
     * @see #getNeedClientAuth()
     * @see #setWantClientAuth(boolean)
     * @see #setUseClientMode(boolean)
     */
    public abstract boolean getWantClientAuth();


    /**
     * Controls whether new SSL sessions may be established by this socket.
     * If session creations are not allowed, and there are no
     * existing sessions to resume, there will be no successful
     * handshaking.
     *
     * @param flag true indicates that sessions may be created; this
     *             is the default.  false indicates that an existing session
     *             must be resumed
     * @see #getEnableSessionCreation()
     */
    public abstract void setEnableSessionCreation(boolean flag);


    /**
     * Returns true if new SSL sessions may be established by this socket.
     *
     * @return true indicates that sessions may be created; this
     * is the default.  false indicates that an existing session
     * must be resumed
     * @see #setEnableSessionCreation(boolean)
     */
    public abstract boolean getEnableSessionCreation();

    /**
     * Returns the SSLParameters in effect for this SSLSocket.
     * The ciphersuites and protocols of the returned SSLParameters
     * are always non-null.
     *
     * @return the SSLParameters in effect for this SSLSocket.
     * @since 1.6
     */
    public SSLParameters getSSLParameters() {
        SSLParameters params = new SSLParameters();
        params.setCipherSuites(getEnabledCipherSuites());
        params.setProtocols(getEnabledProtocols());
        if (getNeedClientAuth()) {
            params.setNeedClientAuth(true);
        } else if (getWantClientAuth()) {
            params.setWantClientAuth(true);
        }
        return params;
    }

    /**
     * Applies SSLParameters to this socket.
     *
     * <p>This means:
     * <ul>
     * <li>If {@code params.getCipherSuites()} is non-null,
     *   {@code setEnabledCipherSuites()} is called with that value.</li>
     * <li>If {@code params.getProtocols()} is non-null,
     *   {@code setEnabledProtocols()} is called with that value.</li>
     * <li>If {@code params.getNeedClientAuth()} or
     *   {@code params.getWantClientAuth()} return {@code true},
     *   {@code setNeedClientAuth(true)} and
     *   {@code setWantClientAuth(true)} are called, respectively;
     *   otherwise {@code setWantClientAuth(false)} is called.</li>
     * <li>If {@code params.getServerNames()} is non-null, the socket will
     *   configure its server names with that value.</li>
     * <li>If {@code params.getSNIMatchers()} is non-null, the socket will
     *   configure its SNI matchers with that value.</li>
     * </ul>
     *
     * @param params the parameters
     * @throws IllegalArgumentException if the setEnabledCipherSuites() or
     *                                  the setEnabledProtocols() call fails
     * @since 1.6
     */
    public void setSSLParameters(SSLParameters params) {
        String[] s;
        s = params.getCipherSuites();
        if (s != null) {
            setEnabledCipherSuites(s);
        }
        s = params.getProtocols();
        if (s != null) {
            setEnabledProtocols(s);
        }
        if (params.getNeedClientAuth()) {
            setNeedClientAuth(true);
        } else if (params.getWantClientAuth()) {
            setWantClientAuth(true);
        } else {
            setWantClientAuth(false);
        }
    }

    // Android-added: Make toString explicit that this is an SSLSocket (http://b/6602228)
    @Override
    public String toString() {
        return "SSL" + super.toString();
    }
}

