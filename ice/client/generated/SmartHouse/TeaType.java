//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.3
//
// <auto-generated>
//
// Generated from file `smarthouse.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package SmartHouse;

public enum TeaType implements java.io.Serializable
{
    BLACK(0),
    GREEN(1),
    WHITE(2);

    public int value()
    {
        return _value;
    }

    public static TeaType valueOf(int v)
    {
        switch(v)
        {
        case 0:
            return BLACK;
        case 1:
            return GREEN;
        case 2:
            return WHITE;
        }
        return null;
    }

    private TeaType(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 2);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, TeaType v)
    {
        if(v == null)
        {
            ostr.writeEnum(SmartHouse.TeaType.BLACK.value(), 2);
        }
        else
        {
            ostr.writeEnum(v.value(), 2);
        }
    }

    public static TeaType ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(2);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<TeaType> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, TeaType v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<TeaType> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            return java.util.Optional.of(ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static TeaType validate(int v)
    {
        final TeaType e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}
