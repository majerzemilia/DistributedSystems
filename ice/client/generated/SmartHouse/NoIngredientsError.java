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

public class NoIngredientsError extends Error
{
    public NoIngredientsError()
    {
        super();
    }

    public NoIngredientsError(Throwable cause)
    {
        super(cause);
    }

    public NoIngredientsError(TimeOfDay errorTime, String reason)
    {
        super(errorTime, reason);
    }

    public NoIngredientsError(TimeOfDay errorTime, String reason, Throwable cause)
    {
        super(errorTime, reason, cause);
    }

    public String ice_id()
    {
        return "::SmartHouse::NoIngredientsError";
    }

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::SmartHouse::NoIngredientsError", -1, false);
        ostr_.endSlice();
        super._writeImpl(ostr_);
    }

    /** @hidden */
    @Override
    protected void _readImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        istr_.endSlice();
        super._readImpl(istr_);
    }

    /** @hidden */
    public static final long serialVersionUID = -1155359019L;
}
